/*
 * Copyright (c) 2016, Mihai Emil Andronache
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  1)Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *  2)Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *  3)Neither the name of charles-github-ejb nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.amihaiemil.charles.github;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcabi.github.Gist;
import com.jcabi.github.Gists;

/**
 * Log file in a Github Gist
 * @author Mihai Andronache (amihaiemil@gmail.com)
 * @version $Id%
 * @since 1.0.0
 *
 */
public class LogsInGist implements LogsLocation {

	private static final Logger LOG = LoggerFactory.getLogger(LogsInGist.class);

	/**
	 * Logs file path.
	 */
    private String logsPath;

    /**
     * Github gists API,
     */
    private Gists gists;

    /**
     * Constructor.
     * @param fileName Logs' file name.
     * @param gistsApi Github gitsts' api.
     */
    public LogsInGist(String filePath, Gists gistsApi) {
        this.logsPath = filePath;
        this.gists = gistsApi;
    }

    /**
     * Writes the log in a secret Github Gist and returns the address.
     */
    @Override
    public String address() {
    	File logsfile = new File(this.logsPath);
        try {
            String logs = FileUtils.readFileToString(new File(this.logsPath));
            Map<String, String> gist = new HashMap<String, String>();
            gist.put(logsfile.getName(), logs);
            Gist created = this.gists.create(gist, false);
            return "https://gist.github.com/" + created.identifier();
        } catch (IOException ex) {
    	    LOG.error(
                "Error when writing the log file " + logsfile.getName() + " to a secret gist. " +
                "If this behaviour persists, please open an issue at https://github.com/amihaiemil/charles-github-ejb",
                ex
            );
    	    return "#";
        }

    }

}