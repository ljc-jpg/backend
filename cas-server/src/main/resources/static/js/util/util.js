var Util = {
    dom: 'http://127.0.0.1:8762',
    token: localStorage.getItem("token"),
    login: 'http://127.0.0.1:8762/cas-server/login',
    request: function (path) {
        console.log("1:", path);
    },
    response: function (path) {
        console.log("2:", path);
    },
    requestFormData: function (path, type, data, successCallback, errorCallback, async, oUrl) {
        var sUrl = Util.dom + oUrl + path;
        $.ajax({
            url: sUrl,
            type: type,
            data: data,
            dataType: "json",
            xhrFields: {
                withCredentials: true
            },
            crossDomain: true,
            async: async,
            beforeSend: function (XMLHttpRequest) {
                XMLHttpRequest.setRequestHeader("X-Requested-With", "XMLHttpRequest");
                XMLHttpRequest.setRequestHeader("token", Util.token);
            },
            success: function (data, textStatus, request) {
                if (data && data.code == 401) {
                    location.href = Util.login;
                } else if (successCallback !== null) {
                    successCallback(data, textStatus, request);
                }
            },
            error: function (xhr, type, exception) {
                if (xhr && xhr.responseText) {
                    var resp = xhr.responseText;
                    if (resp.indexOf('window.location.href') > 0 || resp.indexOf('401') > 0) {
                        window.location.href = "/sso/index.html";
                    } else {
                        errorCallback(xhr, type, exception);
                    }
                } else {
                    errorCallback(xhr, type, exception);
                }
            }
        });
    }

};