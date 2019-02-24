package com.lance.common.util;


import com.lance.common.constant.Globals;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

/**
 * xml转换工具类
 *
 * @author lance
 */
public class JaxbUtil
{
    /**
     * bean转xml字符串
     *
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> String beanToxml(T t)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(t.getClass());
            Marshaller marshaller = context.createMarshaller();
            //编码格式
            marshaller.setProperty(Marshaller.JAXB_ENCODING, Globals.ENCODING_UTF8);
            //是否格式化生成的xml串
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //是否省略xml头信息
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
            StringWriter writer = new StringWriter();
            marshaller.marshal(t, writer);
            return writer.toString().replaceAll(" standalone=\"yes\"", "");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * xml字符串转bean
     *
     * @param xml
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlTobean(String xml, Class<T> clazz)
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (T) unmarshaller.unmarshal(new ByteArrayInputStream(xml.getBytes(Globals.ENCODING_UTF8)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
