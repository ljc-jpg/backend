package com.cloud.service;

import com.cloud.dao.SalaryUserAttrMapper;
import com.cloud.model.SalaryUserAttr;
import com.cloud.utils.UploadResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.cloud.utils.MultipartFileUtils.deleteFile;
import static com.cloud.utils.MultipartFileUtils.getMultipartFile;
import static com.cloud.utils.PdfUtilS.*;

/**
 * @Description:
 * @Author: zhuzheng
 * @Date: 2019/12/11 17:57
 */

@Service
public class SalaryService {

    private String PATH = "/";

    private static final Logger log = LoggerFactory.getLogger(SalaryService.class);

    @Autowired
    private SalaryUserAttrMapper salaryUserAttrMapper;

    @Autowired
    private CaseClientService caseClientService;

    /**
     * @param schId salaryId
     * @Description: 生成pdf 并用feign调用wxm-base的文件上传接口上传 返回pdf文件的路径
     * @author zhu zheng
     * @date 2020/3/24
     */
    public String loadSalaryPdf(OutputStream o, Integer salaryId, Integer schId) throws Exception {
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("salaryId", salaryId);
        searchMap.put("schId", schId);
        List<SalaryUserAttr> attrList = salaryUserAttrMapper.selectSalaryByMap(searchMap);
        if (CollectionUtils.isEmpty(attrList)) {
            return null;
        }
        //PDF是否生成过 生成过直接返回url  没有则生成PDF并且上传至文件服务器 更新数据库
        if (!StringUtils.isEmpty(attrList.get(0).getName())) {
            return attrList.get(0).getName();
        }
        //应发项
        List<SalaryUserAttr> y = new ArrayList<>();
        //扣发项
        List<SalaryUserAttr> k = new ArrayList<>();
        //所有项目
        List<SalaryUserAttr> projects = attrList.get(0).getSalaryUserAttrs();

        float[] tableWidth = new float[2 + projects.size() + 1];
        for (int i = 0; i < 2; i++) {
            tableWidth[i] = 90f;
        }

        for (int i = 0; i < projects.size(); i++) {
            tableWidth[2 + i] = 40f;
            if (1 == projects.get(i).getProjectType()) {
                y.add(projects.get(i));
            } else {
                k.add(projects.get(i));
            }
        }
        tableWidth[2 + projects.size()] = 40f;
        if (!StringUtils.isEmpty(attrList.get(0).getName())) {
            return attrList.get(0).getName();
        }
        //存本地的pdf
        String fileName = attrList.get(0).getSalaryName() + ".pdf";
        String path = PATH + fileName;
        o = new FileOutputStream(path);
        Document pdf = new Document();
        PdfWriter.getInstance(pdf, o);
        pdf.open();
        getOutputStream(o, attrList, y, k, tableWidth, pdf);
        log.error("***********path*********** : " + path);
        //第一个参数需要上传file   第二个参数 为 @RequestPart("multipartFile")   第三个 文件名称
        MultipartFile multipartFile = getMultipartFile(new File(path), "multipartFile", fileName);
        UploadResult result = caseClientService.uploadInputStream(multipartFile, fileName);
        deleteFile(PATH, fileName);
        salaryUserAttrMapper.updatePathById(salaryId, result.getFilePreviewPathFull());
        return result.getFilePreviewPathFull();
    }

    /**
     * @Description: loadSalaryPdf 用到的生成工资pdf工具方法
     * @author zhu zheng
     * @date 2020/3/24
     */
    private OutputStream getOutputStream(OutputStream out, List<SalaryUserAttr> attrList, List<SalaryUserAttr> y, List<SalaryUserAttr> k, float[] tableWidth, Document doc) throws DocumentException, IOException {
        Font titleFont = new Font(BFCHINESE, 16, Font.NORMAL);
        Paragraph paragraph = new Paragraph(attrList.get(0).getSalaryName(), titleFont);
        paragraph.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
        doc.add(paragraph);
        doc.add(new Paragraph("\n"));
        PdfPTable table = createTable(tableWidth);
        //表头
        table.addCell(createCellR("登录号（勿修改）", TITLETEXTFONT, 1, 2));
        table.addCell(createCellR("姓名（勿修改）", TITLETEXTFONT, 1, 2));
        if (!CollectionUtils.isEmpty(y)) {
            table.addCell(createCell("应发项", TITLETEXTFONT, 1, y.size()));
        }
        if (!CollectionUtils.isEmpty(k)) {
            table.addCell(createCell("扣发项", TITLETEXTFONT, 1, k.size()));
        }
        //行合并
        table.addCell(createCell("合计项", TITLETEXTFONT, 1, 3));
        for (SalaryUserAttr yf : y) {
            table.addCell(createCell(yf.getProjectName(), TEXTFONT));
        }
        for (SalaryUserAttr kf : k) {
            table.addCell(createCell(kf.getProjectName(), TEXTFONT));
        }
        table.addCell(createCell("应发合计", TEXTFONT));
        table.addCell(createCell("扣发合计", TEXTFONT));
        table.addCell(createCell("实发工资", TEXTFONT));
        //表数据
        for (SalaryUserAttr a : attrList) {
            table.addCell(createCell(a.getLoginName(), TEXTFONT));
            table.addCell(createCell(a.getFullName(), TEXTFONT));
            List<SalaryUserAttr> attrs = a.getSalaryUserAttrs();
            BigDecimal total = new BigDecimal("0.00");
            BigDecimal addTotal = new BigDecimal("0.00");
            BigDecimal reduceTotal = new BigDecimal("0.00");
            for (SalaryUserAttr s : attrs) {
                String money = null == s.getMoney() ? "0.00" : s.getMoney().toString();
                table.addCell(createCell(money, TEXTFONT));
                if (1 == s.getProjectType()) {
                    total = total.add(new BigDecimal(money));
                    addTotal = addTotal.add(new BigDecimal(money));
                } else {
                    total = total.subtract(new BigDecimal(money));
                    reduceTotal = reduceTotal.subtract(new BigDecimal(money));
                }
            }
            table.addCell(createCell(addTotal.toString(), TEXTFONT));
            table.addCell(createCell(new BigDecimal("0.00").subtract(reduceTotal).toString(), TEXTFONT));
            table.addCell(createCell(total.toString(), TEXTFONT));
        }
        doc.add(table);
        doc.close();
        out.close();
        return out;
    }


    /**
     * @param addressee 收件人邮箱  salaryId指定工资单   schId
     * @Description:
     * @author zhu zheng
     * @date 2020/3/24
     */
    public void sendSalaryEmail(String addressee, Integer salaryId, Integer schId, String userId) throws Exception {
        Map<String, Object> searchMap = new HashMap<>();
        searchMap.put("salaryId", salaryId);
        searchMap.put("schId", schId);
        log.error("searchMap:", searchMap);
        List<SalaryUserAttr> attrList = salaryUserAttrMapper.selectSalaryByMap(searchMap);
        SalaryUserAttr userAttr = attrList.get(0);
        Date approvalTime = userAttr.getCreateTime();
        log.error("approvalTime:", approvalTime);
        //Java8中的LocalDateTime  设置LocalDateTime时间值 参数long
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(approvalTime.getTime()), ZoneId.systemDefault());
        //邮件主题
        String subject = localDateTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd")) + " " + userAttr.getSalaryName() + " 工资单";
        //工资单路径
        OutputStream o = null;
        String path = this.loadSalaryPdf(o, salaryId, schId);
        log.error("***********addressee*************:" + addressee + "***********path*************:" + path);
        String content = "<a href='" + path + "'>点击我查看工资单</a>";
        caseClientService.sendEmails(addressee, content, subject);
    }


}