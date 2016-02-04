package com.example.neethu.qrcodescanneractivity;

/**
 * Created by neethu on 2/2/16.
 */
public class HistoryData {
    private String mQRdata;
    private long mDate;

    public HistoryData(String qrData, long date) {
        this.mQRdata = qrData;
        this.mDate = date;
    }

    public String getUrl() {
        return mQRdata;
    }

    public void setUrl(String mUrl) {
        this.mQRdata = mUrl;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long mDate) {
        this.mDate = mDate;
    }

    @Override
    public String toString() {
        return this.mQRdata+"|"+this.mDate;
    }
}
