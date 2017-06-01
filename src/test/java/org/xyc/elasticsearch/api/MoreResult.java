package org.xyc.elasticsearch.api;

import java.util.Date;

/**
 * Created by CCC on 2016/6/16.
 */
public class MoreResult extends BaseResult {

    private String name;

    private Date firstDate;

    private Long secondDate;

    private String content;

    private String contentNot;

    private String message;

    private Integer age;

    private MoreEnum newDay;

    private Long[] status;

    private String claimStatus;

    private long estimatorUserId;

    private String claimCompanyName;

    private String text1;

    private String text2;

    private String text3;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    public Long getSecondDate() {
        return secondDate;
    }

    public void setSecondDate(Long secondDate) {
        this.secondDate = secondDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentNot() {
        return contentNot;
    }

    public void setContentNot(String contentNot) {
        this.contentNot = contentNot;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public MoreEnum getNewDay() {
        return newDay;
    }

    public void setNewDay(MoreEnum newDay) {
        this.newDay = newDay;
    }

    public Long[] getStatus() {
        return status;
    }

    public void setStatus(Long[] status) {
        this.status = status;
    }

    public String getClaimStatus() {
        return claimStatus;
    }

    public void setClaimStatus(String claimStatus) {
        this.claimStatus = claimStatus;
    }

    public long getEstimatorUserId() {
        return estimatorUserId;
    }

    public void setEstimatorUserId(long estimatorUserId) {
        this.estimatorUserId = estimatorUserId;
    }

    public String getClaimCompanyName() {
        return claimCompanyName;
    }

    public void setClaimCompanyName(String claimCompanyName) {
        this.claimCompanyName = claimCompanyName;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }
}
