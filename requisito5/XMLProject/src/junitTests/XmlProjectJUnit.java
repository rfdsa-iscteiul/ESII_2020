package junitTests;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import xml.XmlProject;

class XmlProjectJUnit {
	private static final String URI = "https://github.com/vbasto-iscte/ESII1920.git";
	public static final String folder = "Rep";
	public static final String file = "covid19spreading.rdf";
	static Document doc;
	static XPath xpath ;
    @BeforeAll
    public static void setup() {
    	try {
    		XmlProject.cloneGit();
		File inputFile = new File(folder + File.separator + file);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		XPathFactory xpathFactory = XPathFactory.newInstance();
		xpath = xpathFactory.newXPath();
    	}catch (Exception e) {
			System.out.println("err");
		}
    }
    
	@Test
	void test1() {
			String result = null;
	         String sampleInput ="regiao1=all&mysubquery1=Infecoes&operator1=all&query1=&logicalOperator1=non&mysubquery2=Infecoes&operator2=all&query2=";
		    try {
		    	
				InputStream targetStream = IOUtils.toInputStream(sampleInput, "utf-8");
				try {
					result = XmlProject.GetResult(targetStream, xpath, doc);
					if(result == null) {
				    	fail("code didnt work");
				    }
				} catch (XPathExpressionException e) {
					fail("xPathExpressionException");
				}
				sampleInput ="regiao2=all&mysubquery1=Infecoes&operator1=all&query1=&logicalOperator1=non&mysubquery2=Infecoes&operator2=all&query2=";
				targetStream = IOUtils.toInputStream(sampleInput, "utf-8");
				try {
					result = XmlProject.GetResult(targetStream, xpath, doc);
					if(result == null) {
				    	fail("code didnt work");
				    }
				} catch (XPathExpressionException e) {
					fail("xPathExpressionException");
				}
				
				sampleInput ="regiao1=qry&mysubquery1=Infecoes&operator1=all&query1=&logicalOperator1=non&mysubquery2=Infecoes&operator2=all&query2=";
				targetStream = IOUtils.toInputStream(sampleInput, "utf-8");
				try {
					result = XmlProject.GetResult(targetStream, xpath, doc);
					if(result == null) {
				    	fail("code didnt work");
				    }
				} catch (XPathExpressionException e) {
				}
				sampleInput ="regiao1=qry&mysubquery1=Testes&operator1=gt&query1=&logicalOperator1=and&mysubquery2=Infecoes&operator2=all&query2=";
				targetStream = IOUtils.toInputStream(sampleInput, "utf-8");
				try {
					result = XmlProject.GetResult(targetStream, xpath, doc);
					if(result == null) {
				    	fail("code didnt work");
				    }
				} catch (XPathExpressionException e) {
					
				}
				
				sampleInput ="regiao1=qry&mysubquery1=Testes&operator1=gt&query1=10&logicalOperator1=and&mysubquery2=Infecoes&operator2=all&query2=";
				targetStream = IOUtils.toInputStream(sampleInput, "utf-8");
				try {
					result = XmlProject.GetResult(targetStream, xpath, doc);
					if(result == null) {
				    	fail("code didnt work");
				    }
				} catch (XPathExpressionException e) {
					
				}
				
				sampleInput ="sum1=sum&regiao1=all&mysubquery1=Testes&operator1=all&query1=10&logicalOperator1=non&mysubquery2=Infecoes&operator2=all&query2=";
				targetStream = IOUtils.toInputStream(sampleInput, "utf-8");
				try {
					result = XmlProject.GetResult(targetStream, xpath, doc);
					if(result == null) {
				    	fail("code didnt work");
				    }
				} catch (XPathExpressionException e) {
				}
				
				sampleInput ="not1=not&not2=not&regiao1=all&mysubquery1=Testes&operator1=all&query1=10&logicalOperator1=or&mysubquery2=Infecoes&operator2=all&query2=";
				targetStream = IOUtils.toInputStream(sampleInput, "utf-8");
				try {
					result = XmlProject.GetResult(targetStream, xpath, doc);
					if(result == null) {
				    	fail("code didnt work");
				    }
				} catch (XPathExpressionException e) {
				}
				
				sampleInput ="regiao1=qry&mysubquery1=Testes&operator1=eq&query1=10&logicalOperator1=non&mysubquery2=Infecoes&operator2=all&query2=";
				targetStream = IOUtils.toInputStream(sampleInput, "utf-8");
				try {
					result = XmlProject.GetResult(targetStream, xpath, doc);
					if(result == null) {
				    	fail("code didnt work");
				    }
				} catch (XPathExpressionException e) {
				}
				
				sampleInput ="regiao1=qry&mysubquery1=Testes&operator1=dif&query1=10&logicalOperator1=non&mysubquery2=Infecoes&operator2=all&query2=";
				targetStream = IOUtils.toInputStream(sampleInput, "utf-8");
				try {
					result = XmlProject.GetResult(targetStream, xpath, doc);
					if(result == null) {
				    	fail("code didnt work");
				    }
				} catch (XPathExpressionException e) {
				}
				
				sampleInput ="regiao1=qry&mysubquery1=Testes&operator1=lt&query1=10&logicalOperator1=non&mysubquery2=Infecoes&operator2=all&query2=";
				targetStream = IOUtils.toInputStream(sampleInput, "utf-8");
				try {
					result = XmlProject.GetResult(targetStream, xpath, doc);
					if(result == null) {
				    	fail("code didnt work");
				    }
				} catch (XPathExpressionException e) {
				}
				
				
				sampleInput ="regiao1=qry&mysubquery1=Testes&operator1=gt&query1=10&logicalOperator1=non&mysubquery2=Infecoes&operator2=all&query2=";
				targetStream = IOUtils.toInputStream(sampleInput, "utf-8");
				try {
					result = XmlProject.GetResult(targetStream, xpath, doc);
					if(result == null) {
				    	fail("code didnt work");
				    }
				} catch (XPathExpressionException e) {
				}
				
				sampleInput ="regiao1=qry&mysubquery1=Testes&operator1=lte&query1=10&logicalOperator1=non&mysubquery2=Infecoes&operator2=all&query2=";
				targetStream = IOUtils.toInputStream(sampleInput, "utf-8");
				try {
					result = XmlProject.GetResult(targetStream, xpath, doc);
					if(result == null) {
				    	fail("code didnt work");
				    }
				} catch (XPathExpressionException e) {
				}
				
				
				sampleInput ="regiao1=qry&mysubquery1=Testes&operator1=gte&query1=10&logicalOperator1=non&mysubquery2=Infecoes&operator2=all&query2=";
				targetStream = IOUtils.toInputStream(sampleInput, "utf-8");
				try {
					result = XmlProject.GetResult(targetStream, xpath, doc);
					if(result == null) {
				    	fail("code didnt work");
				    }
				} catch (XPathExpressionException e) {
				}
				
				
				sampleInput ="regiao1=qry&mysubquery1=Testes&operator1=err&query1=10&logicalOperator1=non&mysubquery2=Infecoes&operator2=all&query2=";
				targetStream = IOUtils.toInputStream(sampleInput, "utf-8");
				try {
					result = XmlProject.GetResult(targetStream, xpath, doc);
					if(result == null) {
				    	fail("code didnt work");
				    }
				} catch (XPathExpressionException e) {
				}
				targetStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				fail("wrong charset");
			}
		    
	}
	
	@Test
    void test2() {
    	try {
			XmlProject.GetPage("lel", xpath, doc);
		} catch (XPathExpressionException | SAXException | IOException | ParserConfigurationException e) {
			fail("bust");
		}
    }

}
