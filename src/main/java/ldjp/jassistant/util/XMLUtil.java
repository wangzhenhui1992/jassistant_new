package ldjp.jassistant.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.JDOMSource;

import ldjp.jassistant.common.PJConst;

/**
 * XML utility class
 *
 */
public class XMLUtil {

	/**
	 * get DOM from XML file input stream
	 *
	 */
	public static Document getDocument(InputStream in) {
		// Create an instance of the tester and test
		try {
			SAXBuilder builder = new SAXBuilder(false);
			Document doc = builder.build(in);
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * get DOM from XML file
	 *
	 */
	public static Document getDocument(File file) {
		// Create an instance of the tester and test
		try {
		    SAXBuilder builder = new SAXBuilder(false);
			Document doc = builder.build(file);
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * get String from document
	 *
	 */
	public static String getDocumentString(Document doc) {
		try {
			XMLOutputter outputter = new XMLOutputter();
			StringWriter out = new StringWriter();
			outputter.output(doc, out);

			return out.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return PJConst.EMPTY;
	}

	/**
	 * get String from Element
	 *
	 */
	public static String getElementString(Element ele) {
		try {
			XMLOutputter outputter = new XMLOutputter();
			StringWriter out = new StringWriter();
			outputter.output(ele, out);

			return out.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return PJConst.EMPTY;
	}

	/**
	 * transform XML and StyleSheet to a text string
	 *
	 * @param in
	 * @param stylesheet
	 */
	public static String transform(Document in, InputStream stylesheet, String encoding)
								throws JDOMException {
		try {
			Transformer transformer = TransformerFactory.newInstance()
											.newTransformer(new StreamSource(stylesheet));

	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "text");
	        transformer.setOutputProperty(OutputKeys.ENCODING, encoding);

			StringWriter out = new StringWriter();
			transformer.transform(new JDOMSource(in), new StreamResult(out));

			return out.toString();
		} catch (TransformerException e) {
			throw new JDOMException("XSLT Transformation failed", e);
		}
	}

	/**
	 * transform XML and StyleSheet to a text string
	 *
	 * @param in
	 * @param stylesheet
	 */
	public static String transform(Document in, File stylesheet, String encoding)
								throws JDOMException, IOException {
		return transform(in, new FileInputStream(stylesheet), encoding);
	}
}
