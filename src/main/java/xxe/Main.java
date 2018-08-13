package xxe;

import java.io.FileInputStream;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class Main
{
	public static void main(String[] args) throws JDOMException, IOException
	{
		FileInputStream fis =new FileInputStream("e://test.xml");
		
		
		SAXBuilder builder = new SAXBuilder(false);
		String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
		// builder.setFeature(FEATURE, true);
		Document document = builder.build(fis);
		org.jdom2.Element e = document.getRootElement();
		String text=e.getText();
		System.out.println(text);
	}
}
