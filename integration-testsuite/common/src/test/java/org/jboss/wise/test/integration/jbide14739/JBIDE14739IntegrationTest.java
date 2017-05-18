/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.wise.test.integration.jbide14739;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.wise.core.client.InvocationResult;
import org.jboss.wise.core.client.WSDynamicClient;
import org.jboss.wise.core.client.WSMethod;
import org.jboss.wise.core.client.builder.WSDynamicClientBuilder;
import org.jboss.wise.core.client.factories.WSDynamicClientFactory;
import org.jboss.wise.core.test.WiseTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.util.Map;

@RunWith(Arquillian.class)
public class JBIDE14739IntegrationTest extends WiseTest {

    private static final String WAR = "jbide14739";

    @Deployment
    public static WebArchive createDeploymentA() {
        WebArchive archive = ShrinkWrap.create(WebArchive.class, WAR + ".war");
        archive.addClass(org.jboss.wise.test.integration.jbide14739.HelloWorldInterface.class)
                .addClass(org.jboss.wise.test.integration.jbide14739.HelloWorldBean.class)
                .setWebXML(new File(getTestResourcesDir() + "/WEB-INF/jbide14739/web.xml"));
        return archive;
    }

    @Test
    @RunAsClient
    public void shouldConsumeNewWsdlAfterEndpointRefresh() throws Exception {
        URL wsdlURL = new URL(getServerHostAndPort() + "/jbide14739/HelloWorld?wsdl");
        runWise(wsdlURL, "target/temp/wise/jbide14739", "echo");
    }

    public static void runWise(URL wsdlURL, String tempDir, String methodName) throws Exception {
        WSDynamicClientBuilder clientBuilder = WSDynamicClientFactory.getJAXWSClientBuilder();
        WSDynamicClient client = clientBuilder.tmpDir(tempDir).verbose(true).keepSource(true).wsdlURL(wsdlURL.toString())
                .build();
        WSMethod method = client.getWSMethod("HelloService", "HelloWorldBeanPort", methodName);
        Map<String, Object> args = new java.util.HashMap<String, Object>();
        args.put("arg0", "from-wise-client");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        method.writeRequestPreview(args, bos);
        Assert.assertTrue("Expected string containing '<arg0>from-wise-client</arg0>' but got: " + bos.toString(), bos
                .toString().contains("<arg0>from-wise-client</arg0>"));
        InvocationResult result = method.invoke(args, null);
        Map<String, Object> res = result.getMapRequestAndResult(null, null);
        @SuppressWarnings("unchecked")
        Map<String, Object> test = (Map<String, Object>) res.get("results");
        client.close();
        Assert.assertEquals("from-wise-client", test.get("result"));
    }
}
