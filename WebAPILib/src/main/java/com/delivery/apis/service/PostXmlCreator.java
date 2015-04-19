package com.delivery.apis.service;

import com.delivery.apis.params.Parameters;
import com.thoughtworks.xstream.XStream;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by s on 19-04-2015.
 */
public class PostXmlCreator {

    public String createPostXml(Parameters params){

        XStream xs = new XStream();
        xs.autodetectAnnotations(true);
        /*xs.alias("Carrot", Parameters.class);
        xs.alias("Adapter", Adapter.class);*/
        String xml = xs.toXML(params);
        xml = StringEscapeUtils.unescapeHtml4(xml);
        return xml;
    }


}