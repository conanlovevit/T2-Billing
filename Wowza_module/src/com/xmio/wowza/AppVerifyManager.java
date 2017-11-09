package com.xmio.wowza;

import com.vtcmobile.common.SecurityManager;

import com.wowza.wms.module.*;
import com.wowza.wms.client.*;
import com.wowza.wms.amf.*;
import com.wowza.wms.application.IApplicationInstance;
import com.wowza.wms.httpstreamer.cupertinostreaming.file.IHTTPStreamerCupertinoIndex;
import com.wowza.wms.httpstreamer.cupertinostreaming.file.IHTTPStreamerCupertinoIndexItem;
import com.wowza.wms.httpstreamer.cupertinostreaming.httpstreamer.HTTPStreamerApplicationContextCupertinoStreamer;
import com.wowza.wms.httpstreamer.cupertinostreaming.httpstreamer.IHTTPStreamerCupertinoVODActionNotify;
import com.wowza.wms.httpstreamer.cupertinostreaming.livestreampacketizer.LiveStreamPacketizerCupertinoChunk;
import com.wowza.wms.httpstreamer.model.IHTTPStreamerApplicationContext;
import com.wowza.wms.httpstreamer.model.IHTTPStreamerSession;
import com.wowza.wms.request.*;
import com.wowza.wms.rtp.model.RTPSession;

import com.wowza.wms.stream.MediaStream;

import com.wowza.wms.util.ModuleUtils;

import com.xmio.wowza.manager.AppViewUpdate;
import com.xmio.wowza.manager.Params;
import com.xmio.wowza.utils.TokenItem;

import com.xmio.wowza.utils.TokenManager;

import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;

public class AppVerifyManager extends ModuleBase {
    public void onAppStart(IApplicationInstance appInstance) {
        getLogger().info("onAppStart");
    }

    public void onHTTPSessionCreate(IHTTPStreamerSession httpSession) {
        try {
            if (!TokenManager.verify(httpSession.getQueryStr(), httpSession.getUri().split(":", 2)[1], httpSession.getIpAddress()))
                httpSession.rejectSession();
        } catch (Exception ex) {
            httpSession.rejectSession();
        }
    }

    public void onRTPSessionCreate(RTPSession rtpSession) { // RTSP --> reject
        getLogger().info("onRTPSessionCreate --> reject");
        rtpSession.rejectSession();
    }

    public void onConnect(IClient client, RequestFunction function, AMFDataList params) { // RTMP --> reject
        getLogger().info("onConnect --> reject");
        client.shutdownClient();
    }
}
