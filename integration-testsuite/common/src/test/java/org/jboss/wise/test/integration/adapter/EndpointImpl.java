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
package org.jboss.wise.test.integration.adapter;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@WebService(targetNamespace = "http://www.jboss.org/wise/adapter/", name = "Endpoint", serviceName = "EndpointService", portName = "EndpointPort", endpointInterface = "org.jboss.wise.test.integration.adapter.Endpoint", wsdlLocation = "WEB-INF/wsdl/EndpointService.wsdl")
public class EndpointImpl implements Endpoint {

    @WebResult(name = "mimepart", targetNamespace = "http://www.jboss.org/wise/adapter/", partName = "mimepart")
    @XmlJavaTypeAdapter(value = HexBinaryAdapter.class)
    @WebMethod
    public byte[] getData(
            @WebParam(partName = "dataQuery", mode = WebParam.Mode.INOUT, name = "dataQuery") javax.xml.ws.Holder<DataQuery> dataQuery) {
        return new byte[] { 0, 1 };
    }
}
