package com.content_log.ejb.api;

import com.content_log.ejb.publicItems.BandWidthViewItem;
import com.content_log.ejb.publicItems.ViewItem;

import java.util.Date;
import java.util.List;

import java.util.Map;

import javax.ejb.Remote;
@Remote
public interface Report {
     List<ViewItem> reportView(String fromDate,String toDate) throws Exception;
     List<BandWidthViewItem> reportBandWidthView(String fromDate,String toDate) throws Exception;
}
