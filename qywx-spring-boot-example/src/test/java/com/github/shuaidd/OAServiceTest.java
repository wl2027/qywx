package com.github.shuaidd;

import com.github.shuaidd.dto.checkin.CheckInData;
import com.github.shuaidd.dto.checkin.CheckInRule;
import com.github.shuaidd.dto.checkin.SetCheckInScheduleItem;
import com.github.shuaidd.dto.tool.DialRecord;
import com.github.shuaidd.response.oa.CheckInDayReportResponse;
import com.github.shuaidd.response.oa.CheckInOptionResponse;
import com.github.shuaidd.response.oa.CheckInScheduleResponse;
import com.github.shuaidd.resquest.oa.*;
import com.github.shuaidd.resquest.tool.DialRecordRequest;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

/**
 * 描述 OA 接口测试用例
 *
 * @author ddshuai
 * date 2021-07-15 09:16
 **/
public class OAServiceTest extends AbstractTest {

    /*打卡应用*/
    public static final String CHECK_IN = "check-in";

    /*公费电话应用*/
    public static final String PUBLIC_TELEPHONE = "public-telephone";

    @Test
    public void getCorpCheckInOption() {
        CheckInOptionResponse checkInOptionResponse = weChatManager.oaService().getCorpCheckInOption(CHECK_IN);
        logger.info("获取到的打卡数据--{}", checkInOptionResponse);
    }

    @Test
    public void getDialRecord() {
        DialRecordRequest recordRequest = new DialRecordRequest();
        List<DialRecord> dialRecords = weChatManager.oaService().getDialRecord(recordRequest, PUBLIC_TELEPHONE);
        logger.info("获取到的公费电话拨打记录数据--{}", dialRecords);
    }

    @Test
    public void getCheckInData() {
        CheckInDataRequest request = new CheckInDataRequest();
        request.setOpenCheckInDataType(3);
        request.setStartTime(getUnixTime("2021-07-15 00:00:00"));
        request.setEndTime(getUnixTime("2021-07-15 23:59:59"));
        request.setUserIdList(Collections.singletonList("20170410022717"));
        List<CheckInData> checkInData = weChatManager.oaService().getCheckInData(request, CHECK_IN);
        logger.info("获取到的打卡数据--{}", checkInData);
    }

    @Test
    public void getCheckInOption() {
        CheckInRuleRequest request = new CheckInRuleRequest();
        request.setUserIdList(Collections.singletonList("20200914034599"));
        request.setDateTime(getUnixTime("2021-07-16 00:00:00"));
        List<CheckInRule> checkInRules = weChatManager.oaService().getCheckInOption(request, CHECK_IN);
        logger.info("获取到的员工打卡规则--{}", checkInRules);
    }

    @Test
    public void getCheckInDayData() {
        CommonOaRequest request = new CommonOaRequest();
        request.setStartTime(getUnixTime("2021-07-15 00:00:00"));
        request.setEndTime(getUnixTime("2021-07-16 00:00:00"));
        request.setUserIdList(Collections.singletonList("20170410022717"));
        CheckInDayReportResponse reportResponse = weChatManager.oaService().getCheckInDayData(request, CHECK_IN);
        logger.info("获取打卡日报数据--{}", reportResponse);
    }

    @Test
    public void getCheckInMonthData() {
        CommonOaRequest request = new CommonOaRequest();
        request.setStartTime(getUnixTime("2021-07-01 00:00:00"));
        request.setEndTime(getUnixTime("2021-07-20 00:00:00"));
        request.setUserIdList(Collections.singletonList("20170410022717"));
        CheckInDayReportResponse reportResponse = weChatManager.oaService().getCheckInMonthData(request, CHECK_IN);
        logger.info("获取打卡月报数据--{}", reportResponse);
    }

    @Test
    public void getCheckInScheduleList() {
        CommonOaRequest request = new CommonOaRequest();
        request.setStartTime(getUnixTime("2021-07-01 00:00:00"));
        request.setEndTime(getUnixTime("2021-07-20 00:00:00"));
        request.setUserIdList(Collections.singletonList("20170410022717"));
        CheckInScheduleResponse reportResponse = weChatManager.oaService().getCheckInScheduleList(request, CHECK_IN);
        logger.info("获取打卡人员排班信息数据--{}", reportResponse);
    }

    /**
     * 需要设置当前时间之后的日期，否则会出现下面的错误
     * day or monthyear error
     */
    @Test
    public void setCheckInScheduleList() {
        SetCheckInScheduleRequest request = new SetCheckInScheduleRequest();
        request.setGroupId(4);
        request.setYearMonth("202107");

        SetCheckInScheduleItem scheduleItem = new SetCheckInScheduleItem();
        scheduleItem.setDay(18);
        scheduleItem.setUserId("20200914034599");
        scheduleItem.setScheduleId(0);

        request.setItems(Collections.singletonList(scheduleItem));
        weChatManager.oaService().setCheckInScheduleList(request, CHECK_IN);
        logger.info("设置排班信息成功");
    }

    @Test
    public void addCheckInUserFace(){
        AddCheckInUserFaceRequest request = new AddCheckInUserFaceRequest();
        request.setUserFace("sdsds");
        request.setUserId("20200914034599");
        weChatManager.oaService().addCheckInUserFace(request,CHECK_IN);
    }

    private Long getUnixTime(String date) {
        try {
            return DateUtils.parseDate(date, "yyyy-MM-dd HH:mm:ss").getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
