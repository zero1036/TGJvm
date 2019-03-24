var invitationInfo = null;
function getTargetIdPub() {
    var targetId = '';
    invitationInfo = queryMessage("oms", "/cms/userRelation/api/x/v/findInviterByUserId", "userId=" + evt.userId, false);
    if (invitationInfo != null) {
        targetId = invitationInfo.inviterId;
    }
    return targetId;
}
function satisfiedPub(re) {
    //TODO: pass
    if (evt.cardStatus != 400 && evt.cardStatus != 450) {
        return output(false, [], []);
    }

    //TODO: pass
    /*产品类型*/
    if (!(contain(tmpl.productType, '99', true) || (contain(tmpl.productType, String(productTypes[evt.cardType]), true)))) {
        return output(false, [], []);
    }


    /*授信通过*/
    if ((tmpl.approveFirst == 1 || tmpl.approveFirst == "1") && (contain(tmpl.productType, '99', true) && !evt.myFirstBlood) || (contain(tmpl.productType, String(productTypes[evt.cardType]), true) && !evt.firstBlood)) {
        return output(false, [], []);
    }
    /*授信额度*/
    var creditLineLow = tmpl.creditPartners.creditAmountLow;
    var creditLineHigh = tmpl.creditPartners.creditAmountHigh;
    var ok1 = validateRanage(creditLineLow, evt.creditLine, creditLineHigh);
    if (!ok1) {
        return output(false, [], []);
    }
    /*邀请人判断*/
    if (invitationInfo == undefined || invitationInfo == null || invitationInfo.inviterId == null || invitationInfo.inviterId == '') {
        return output(false, [], []);
    }
    var filter = new java.util.HashMap();
    filter.put("data.inviter =", invitationInfo.inviterId);
    var partnerIds = queryEvent([], "com.jd.common.vo.collect.response.RegisteredResp", re, filter, function (event) {
        return event.userId;
    });
    var totalCount = 0;
    if (!checkNull(partnerIds) && partnerIds.length > 0) {
        var filterCredit = new java.util.HashMap();
        filterCredit.put("data.userId in", Java.to(partnerIds, 'java.lang.String[]'));
        var filterStr = "";
        if (tmpl.creditAmountTmpl.creditAmountLow != undefined && tmpl.creditAmountTmpl.creditAmountLow != null && tmpl.creditAmountTmpl.creditAmountLow != 0) {
            filterCredit.put("data.creditLine >=", tmpl.creditAmountTmpl.creditAmountLow);
        }
        if (tmpl.creditAmountTmpl.creditAmountHigh != undefined && tmpl.creditAmountTmpl.creditAmountHigh != null && tmpl.creditAmountTmpl.creditAmountHigh != 0) {
            filterCredit.put("data.creditLine <=", tmpl.creditAmountTmpl.creditAmountHigh);
        }
        if (tmpl.approveStatus == 1 || tmpl.approveStatus == "1") {
            filterCredit.put("data.cardStatus =", evt.cardStatus);
        }
        if (tmpl.approveStatus == 2 || tmpl.approveStatus == "2") {
            filterCredit.put("data.cardStatus !=", evt.cardStatus);
        }
        if (contain(tmpl.productType, String(productTypes[evt.cardType]), true)) {
            filterCredit.put("data.cardType =", productTypes[evt.cardType]);
        }
        if (tmpl.approveFirst == 1 || tmpl.approveFirst == "1") {
            filterCredit.put("data.myFirstBlood =", true);
        }
        var filterCreditPartnerIds = queryEvent([], "com.jd.common.vo.collect.response.CreditResultResp", re, filterCredit, function (event) {
            return event.userId;
        });
        if (!checkNull(filterCreditPartnerIds) && filterCreditPartnerIds.length > 0) {
            totalCount = filterCreditPartnerIds.length;
        }
    } else {
        return output(false, ['好友邀请注册信息为空'], []);
    }
    if (totalCount === 0) {
        return output(false, ['邀请进件人数为空'], []);
    }
    var countLow = tmpl.creditPartners.creditPartnerCountLow;
    var countHigh = tmpl.creditPartners.creditPartnerCountHigh;
    var ok = validateRanage(countLow, totalCount, countHigh);
    return output(ok, [invitationInfo.inviterId], [], totalCount);
}
 