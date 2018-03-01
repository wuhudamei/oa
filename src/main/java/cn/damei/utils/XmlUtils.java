package cn.damei.utils;
import com.sun.xml.bind.marshaller.CharacterEscapeHandler;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class XmlUtils {
    private XmlUtils() {
        super();
    }
    public static String toXml(Object o) {
        String result = null;
        StringWriter writer = null;
        try {
            JAXBContext context = JAXBContext.newInstance(o.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            m.setProperty("com.sun.xml.bind.marshaller.CharacterEscapeHandler", new CharacterEscapeHandler() {
                @Override
                public void escape(char[] chars, int start, int len, boolean b, Writer writer) throws IOException {
                    writer.write(chars, start, len);
                }
            });
            writer = new StringWriter();
            m.marshal(o, writer);
            result = writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(writer);
        }
        return result;
    }
    public static <T> T fromXml(String xml, Class<T> clazz) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller u = context.createUnmarshaller();
            JAXBElement<T> root = u.unmarshal(new StreamSource(new StringReader(xml)), clazz);
            t = root.getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
    public static <T> T fromXml(InputStream is, Class<T> clazz) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller u = context.createUnmarshaller();
            JAXBElement<T> root = u.unmarshal(new StreamSource(), clazz);
            t = root.getValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
    public static Map<String, Object> xml2Map(String xml) {
        Map<String, Object> map = new HashMap();
        if (StringUtils.isNotBlank(xml)) {
            SAXReader reader = new SAXReader();
            Document document = null;
            StringReader sr = null;
            try {
                sr = new StringReader(xml);
                document = reader.read(sr);
                document.getRootElement();
                org.dom4j.Element root = document.getRootElement();
                List<org.dom4j.Element> elementList = root.elements();
                for (org.dom4j.Element e : elementList)
                    map.put(e.getName(), e.getText());
            } catch (DocumentException e) {
                e.printStackTrace();
                return map;
            } finally {
                IOUtils.closeQuietly(sr);
            }
        }
        return map;
    }
    public static Map<String, String> xml2Map(InputStream is) {
        Map<String, String> map = new HashMap();
        if (is != null) {
            SAXReader reader = new SAXReader();
            Document document = null;
            try {
                document = reader.read(is);
                document.getRootElement();
                org.dom4j.Element root = document.getRootElement();
                List<org.dom4j.Element> elementList = root.elements();
                for (org.dom4j.Element e : elementList)
                    map.put(e.getName(), e.getText());
            } catch (DocumentException e) {
                e.printStackTrace();
                return map;
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
        return map;
    }
}
