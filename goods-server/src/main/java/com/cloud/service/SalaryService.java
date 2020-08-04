package com.cloud.service;

import com.alibaba.fastjson.JSON;
import com.cloud.dao.SalaryMapper;
import com.cloud.model.SalaryUserAttr;
import com.cloud.util.ActiveEnum;
import com.cloud.util.UploadResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import static com.cloud.util.DateUtil.*;
import static com.cloud.util.MultipartFileUtils.deleteFile;
import static com.cloud.util.MultipartFileUtils.getMultipartFile;
import static com.cloud.util.PdfUtilS.*;

/**
 * @author zhu zheng
 * @date 2020/3/24
 */

@Service
public class SalaryService {

    private static String PATH = "/";

    private static final Logger logger = LoggerFactory.getLogger(SalaryService.class);

    @Resource
    private SalaryMapper salaryMapper;

    @Resource
    private SalaryUserAttrService salaryUserAttrService;

    @Resource
    private CaseClientService caseClientService;

    /**
     * 生成pdf 并用feign调用wxm-base的文件上传接口上传 返回pdf文件的路径
     *
     * @param salaryId
     * @param schId
     * @return {@link String}
     * @author zhuz
     * @date 2020/8/4
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public String loadSalaryPdf(Integer salaryId, Integer schId) {
        Map<String, Integer> searchMap = new HashMap<>(ActiveEnum.TWO_EVENT.getKey());
        searchMap.put("salaryId", salaryId);
        searchMap.put("schId", schId);
        List<SalaryUserAttr> attrList = salaryUserAttrService.selectSalaryByMap(searchMap);
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
        float[] tableWidth = new float[ActiveEnum.FOUR_EVENT.getKey() + projects.size() + 1];
        for (int i = 0; i < ActiveEnum.TWO_EVENT.getKey(); i++) {
            tableWidth[i] = 60f;
        }
        for (int i = 0; i < projects.size(); i++) {
            tableWidth[ActiveEnum.TWO_EVENT.getKey() + i] = 40f;
            if (1 == projects.get(i).getProjectType()) {
                y.add(projects.get(i));
            } else {
                k.add(projects.get(i));
            }
        }
        for (int i = 0; i < ActiveEnum.THREE_EVENT.getKey(); i++) {
            tableWidth[ActiveEnum.TWO_EVENT.getKey() + i + projects.size()] = 40f;
        }
        //存本地的pdf
        String fileName = attrList.get(0).getSalaryName() + ".pdf";
        String path = PATH + fileName;
        try (OutputStream o = new FileOutputStream(path)) {
            Document pdf = new Document();
            PdfWriter.getInstance(pdf, o);
            pdf.open();
            getOutputStream(o, attrList, y, k, tableWidth, pdf);
        } catch (Exception e) {
            logger.error("loadSalaryPdf生成pdf文件异常:", e);
            throw new RuntimeException("loadSalaryPdf生成pdf文件异常:" + e);
        }

        //第一个参数需要上传file   第二个参数 为 @RequestPart("multipartFile")   第三个 文件名称
        MultipartFile multipartFile = getMultipartFile(new File(path), "multipartFile", fileName);
        UploadResult result = caseClientService.uploadInputStream(multipartFile, fileName);

        deleteFile(PATH, fileName);
        salaryMapper.updatePathById(salaryId, result.getFilePreviewPathFull());

        return result.getFilePreviewPathFull();
    }

    /**
     * 用于生成工资pdf工具方法
     *
     * @author zhu zheng
     * @date 2020/3/24
     */
    private OutputStream getOutputStream(OutputStream out, List<SalaryUserAttr> attrList, List<SalaryUserAttr> y, List<SalaryUserAttr> k, float[] tableWidth, Document doc) throws DocumentException, IOException {
        Font titleFont = new Font(BFCHINESE, 16, Font.NORMAL);
        Paragraph paragraph = new Paragraph(attrList.get(0).getSalaryName(), titleFont);
        //设置文字居中 0靠左   1，居中     2，靠右
        paragraph.setAlignment(1);
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
     * 工资单pdf发送指指定邮件
     *
     * @param addressee 收件人邮箱
     * @param salaryId  salaryId指定工资单
     * @param schId
     * @return
     * @author zhuz
     * @date 2020/8/4
     */
    public void sendSalaryEmail(String addressee, Integer salaryId, Integer schId) {
        Map searchMap = new HashMap<>(ActiveEnum.TWO_EVENT.getKey());
        searchMap.put("salaryId", salaryId);
        searchMap.put("schId", schId);
        logger.info("searchMap:" + searchMap);

        List<SalaryUserAttr> attrList = salaryUserAttrService.selectSalaryByMap(searchMap);
        SalaryUserAttr userAttr = attrList.get(0);
        Date approvalTime = userAttr.getCreateTime();

        logger.info("approvalTime:" + approvalTime);
        //Date转 LocalDateTime 再转 2016年10月05日格式时间
        //邮件主题
        String subject = formatLocalDateTimeToStr(dateToLocalDateTime(approvalTime), DATE_TIME_FORMAT_YYYY年MM月DD日) + " " + userAttr.getSalaryName() + " 工资单";
        //工资单路径
        String path = this.loadSalaryPdf(salaryId, schId);
        logger.info("***********addressee*************:" + addressee + "***********path*************:" + path);
        //邮件主题内容
        String content = "<a href='" + path + "'>点击我查看工资单</a>";
        List<Map<String, String>> param = new ArrayList<>();
        Map<String, String> map = new HashMap<>(ActiveEnum.TWO_EVENT.getKey());
        map.put("content", content);
        map.put("type", "1");
        param.add(map);
        caseClientService.sendEmails(addressee, JSON.toJSONString(param), subject);
    }

}