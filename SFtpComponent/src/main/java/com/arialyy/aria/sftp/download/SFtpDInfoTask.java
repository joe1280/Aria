/*
 * Copyright (C) 2016 AriaLyy(https://github.com/AriaLyy/Aria)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arialyy.aria.sftp.download;

import com.arialyy.aria.core.common.CompleteInfo;
import com.arialyy.aria.core.download.DTaskWrapper;
import com.arialyy.aria.sftp.AbsSFtpInfoTask;
import com.arialyy.aria.util.CommonUtil;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import java.io.UnsupportedEncodingException;

/**
 * 进行登录，获取session，获取文件信息
 */
final class SFtpDInfoTask extends AbsSFtpInfoTask<DTaskWrapper> {

  SFtpDInfoTask(DTaskWrapper wrapper) {
    super(wrapper);
  }

  @Override protected void getFileInfo(Session session) throws JSchException,
      UnsupportedEncodingException, SftpException {
    ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
    SftpATTRS attr = channel.stat(
        CommonUtil.convertFtpChar(getOption().getCharSet(), getWrapper().getEntity().getUrl()));
    getWrapper().getEntity().setFileSize(attr.getSize());
    CompleteInfo info = new CompleteInfo();
    info.code = 200;
    info.obj = channel;
    callback.onSucceed(getWrapper().getKey(), info);
  }
}
