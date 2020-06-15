package xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jgit.api.Git;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import cgi.cgi_lib;

public class XmlProject {

	private static final String URI = "https://github.com/vbasto-iscte/ESII1920.git";
	public static final String folder = "Rep";
	public static final String file = "covid19spreading.rdf";

	/**
	 * @param awnser awser to be put on the result field
	 * @param xpath xpath to use to create queries 
	 * @param doc doc to query
	 * @return	a html page as a string
	 * @throws XPathExpressionException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	public static String GetPage(String awnser, XPath xpath, Document doc)
			throws XPathExpressionException, SAXException, IOException, ParserConfigurationException {

		String query = "/RDF/NamedIndividual/@*";
		ArrayList<String> regioes = new ArrayList<String>();
		ArrayList<String> subtypes = new ArrayList<String>();
		XPathExpression expr = xpath.compile(query);
		NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		for (int i = 0; i < nl.getLength(); i++) {
			regioes.add(StringUtils.substringAfter(nl.item(i).getNodeValue(), "#"));
		}

		query = "/RDF/DatatypeProperty/@*";
		expr = xpath.compile(query);
		NodeList nl2 = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
		for (int i = 0; i < nl2.getLength(); i++) {
			subtypes.add(StringUtils.substringAfter(nl2.item(i).getNodeValue(), "#"));
		}
		File htmlTemplateFile = new File("templates/test.html");
		String htmlString = "Content-type: text/html\n\n";
		htmlString += FileUtils.readFileToString(htmlTemplateFile);
		String subtipesOptions = "";
		for (String string : subtypes) {
			subtipesOptions += "'<option value=\"" + string + "\">" + string + "</option>'+";

		}

		String regioesOptions = "";
		for (String string : regioes) {
			regioesOptions += "'<option value=\"" + string + "\">" + string + "</option>'+";
		}
		htmlString = htmlString.replace("$subquery", subtipesOptions);
		htmlString = htmlString.replace("$regiao", regioesOptions);
		htmlString = htmlString.replace("$Result", awnser);
//		File resultFile = new File("covid_querry.html");
//		resultFile.createNewFile();
//		try (PrintWriter pw = new PrintWriter(resultFile,"utf-8")){
//			pw.write(htmlString);
//		}
		return htmlString;
	}

	/**
	 * @param in input stream to receive the form submit
	 * @param xpath xpath to use to create queries 
	 * @param doc doc to query
	 * @return result to be displayed on the page
	 * @throws XPathExpressionException in case of invalid queries
	 */
	public static String GetResult(InputStream in, XPath xpath, Document doc) throws XPathExpressionException {
		String result = "";

		// Here is a minimalistic CGI program that uses cgi_lib
		// Print the required CGI header.

		// Parse the form data into a Hashtable.

		Hashtable form_data = cgi_lib.ReadParse(in);

		// Create the Top of the returned HTML page
		// System.out.println("Here are the name/value pairs from the form:");

		// Print the name/value pairs sent from the browser.
		// System.out.println(cgi_lib.Variables(form_data));

		String sum = (form_data.get("sum1") == null) ? "null" : form_data.get("sum1").toString();
		String not1 = (form_data.get("not1") == null) ? "null" : form_data.get("not1").toString();
		String not2 = (form_data.get("not2") == null) ? "null" : form_data.get("not2").toString();
		String op1 = (form_data.get("operator1") == null) ? "null" : form_data.get("operator1").toString();
		String op2 = (form_data.get("operator2") == null) ? "null" : form_data.get("operator2").toString();
		String logic = (form_data.get("logicalOperator1") == null) ? "null"
				: form_data.get("logicalOperator1").toString();
		String region = (form_data.get("regiao1") == null) ? "null" : form_data.get("regiao1").toString();
		String arg1 = (form_data.get("query1") == null) ? "null" : form_data.get("query1").toString();
		String arg2 = (form_data.get("query2") == null) ? "null" : form_data.get("query2").toString();
		String param1 = (form_data.get("mysubquery1") == null) ? "null" : form_data.get("mysubquery1").toString();
		String param2 = (form_data.get("mysubquery2") == null) ? "null" : form_data.get("mysubquery2").toString();
//System.out.println(sum);
//System.out.println(not1);
//System.out.println(not2);
//System.out.println(op1);
//System.out.println(op2);
//System.out.println(logic);
//System.out.println(region);
//System.out.println(arg1);
//System.out.println(arg2);
//System.out.println(param1);
//System.out.println(param2);
//
//System.out.println("<br>");

		String query = "";
		String info = "";
		String condition = "";
		String subCondition1 = "";
		String subCondition2 = "";
//System.out.println("-----");
//System.out.println(op1);
		if (op1.equals("all")) {
			subCondition1 = param1;
		} else {
			subCondition1 = param1 + " " + opToString(op1) + " " + arg1;
		}
		if (logic.equals("non")) {
			if (not1.equals("not")) {
				condition = "[not(" + subCondition1 + ")]";
			} else {
				condition = "[" + subCondition1 + "]";
			}

		} else {
			if (op2.equals("all")) {
				subCondition2 = param2;
			} else {
				subCondition2 = param2 + " " + opToString(op2) + " " + arg2;
			}
			if (not1.equals("not")) {
				condition = "[not(" + subCondition1 + ") " + logic;
			} else {
				condition = "[" + subCondition1 + " " + logic;
			}
			if (not2.equals("not")) {
				condition += " not(" + subCondition2 + ")]";
			} else {
				condition += " " + subCondition2 + "]";
			}

		}
//System.out.println(condition);
		switch (region) {
		case "qry":
			info += "qry";
			query = "/RDF/NamedIndividual" + condition + "/@*";
			break;

		case "all":
			if (form_data.get("sum1") == null) {

				info += "all";
				query = "//*" + condition + "/" + param1 + "/text() | /RDF/NamedIndividual" + condition + "/@*";
			} else {
				info += "all sum";
				query = "sum(//*" + condition + "/" + form_data.get("mysubquery1") + "/text())";
			}
			break;
		default:
			info += region;
			query = "//*[contains(@about,'" + region + "')]/" + param1 + "/text() ";
			break;
		}
//System.out.println("<br>");
//System.out.println(info);	
//System.out.println("<br>");
		result += query + "<br> <br>";
//System.out.println("<br>");

		XPathExpression expr = xpath.compile(query);

		if (region.equals("all")) {
			if (sum.equals("sum")) {
				result += expr.evaluate(doc, XPathConstants.STRING) + "<br>";
			} else {
				NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

				for (int i = 0; i < nl.getLength(); i++) {
					try {
						Integer.parseInt(nl.item(i).getNodeValue());
						result += nl.item(i).getNodeValue() + "<br>";
					} catch (Exception e) {
						result += StringUtils.substringAfter(nl.item(i).getNodeValue(), "#") + "<br>";
					}
					// System.out.println(nl.item(i).getNodeValue());
				}

			}
		} else if (region.equals("qry")) {
			NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

			for (int i = 0; i < nl.getLength(); i++) {
				try {
					Integer.parseInt(nl.item(i).getNodeValue());
					result += (nl.item(i).getNodeValue()) + "<br>";
				} catch (Exception e) {
					result += StringUtils.substringAfter(nl.item(i).getNodeValue(), "#") + "<br>";
				}
				// System.out.println(nl.item(i).getNodeValue());
			}
		} else {

			result += (expr.evaluate(doc, XPathConstants.STRING)) + "<br>";
			// System.out.println(nl.item(i).getNodeValue());

		}

		return result;
	}

	/**
	 * gets the latest version of the git rep used to fetch the data
	 */
	public static void cloneGit() {
		try {
			// Deletes the directory in order to always use fresh information
			FileUtils.deleteDirectory(new File(folder));

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// Using the JGit dependency, the repo is cloned
			Git.cloneRepository().setURI(URI).setDirectory(new File(folder)).call();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param op option code from the html select method
	 * @return a string with the operator to be used to genertate the querties
	 */
	private static String opToString(String op) {
		String val = "";
		switch (op) {
		case "dif":
			val = "!=";
			break;
		case "eq":
			val = "=";
			break;
		case "lt":
			val = "<";
			break;
		case "gt":
			val = ">";
			break;
		case "lte":
			val = "<=";
			break;
		case "gte":
			val = ">=";
			break;

		default:
			val = null;
			break;
		}
		return val;
	}

	/**
	 * @param args GET or POST GET returns a html page with a form and POST returns
	 *             the same page with the form and the query result
	 */
	public static void main(String[] args) {
		try {

			cloneGit();
			File inputFile = new File(folder + File.separator + file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			if (args[0].equals("GET")) {
				try {

					String htmlString = GetPage("nothing to see here", xpath, doc);
					System.out.println(htmlString);

				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (args[0].equals("POST")) {
				String result = GetResult(System.in, xpath, doc);
				String htmlString = GetPage(result, xpath, doc);
				System.out.println(htmlString);
			}

//			else if (args[0].equals("TEST")) {
//				try {
//
//					ArrayList<String> regioes = new ArrayList<String>();
//					ArrayList<String> subtypes = new ArrayList<String>();
//
//					String query = "/RDF/NamedIndividual/@*";
//
//					XPathExpression expr = xpath.compile(query);
//					NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
//
//					for (int i = 0; i < nl.getLength(); i++) {
//						System.out.println(StringUtils.substringAfter(nl.item(i).getNodeValue(), "#"));
//					}
//
//					query = "/RDF/NamedIndividual[Infecoes > 50]/@*";
//					System.out.println("Query para obter as regioes com testes " + query);
//					expr = xpath.compile(query);
//					System.out.println(expr.evaluate(doc, XPathConstants.STRING));
//					nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
//
//					for (int i = 0; i < nl.getLength(); i++) {
//						System.out.println(StringUtils.substringAfter(nl.item(i).getNodeValue(), "#"));
//					}
//					query = "//*[Testes > 50 or not(Internamentos)]/Testes/text() | /RDF/NamedIndividual[Testes > 50 or not(Internamentos)]/@*";
//					System.out.println("Query para obter o número de infeções no Algarve: " + query);
//					expr = xpath.compile(query);
//					nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
//					for (int i = 0; i < nl.getLength(); i++) {
//						try {
//							Integer.parseInt(nl.item(i).getNodeValue());
//							System.out.println(nl.item(i).getNodeValue());
//						} catch (Exception e) {
//							System.out.println(StringUtils.substringAfter(nl.item(i).getNodeValue(), "#"));
//						}
//						// System.out.println(nl.item(i).getNodeValue());
//					}
//
//					query = "sum(//*[Testes > 10]/Testes/text())";
//					System.out.println("Query para obter o número de internamentos no Algarve: " + query);
//					expr = xpath.compile(query);
//					System.out.println(expr.evaluate(doc, XPathConstants.STRING));
//
//					query = "//*[contains(@about,'Algarve')]/Testes/text()";
//					System.out.println("Query para obter o número de Testes no Algarve: " + query);
//					expr = xpath.compile(query);
//					System.out.println(expr.evaluate(doc, XPathConstants.STRING));
//
//					query = "//*[contains(@about,'Algarve')]/*";
//					System.out.println("Query para obter o número de Testes no Algarve: " + query);
//					expr = xpath.compile(query);
//					System.out.println(expr.evaluate(doc, XPathConstants.STRING));
//
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//			}
		} catch (Exception e) {
			System.out.println(cgi_lib.Header());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String exceptionAsString = sw.toString();
			System.out.println(exceptionAsString);
			System.out.println(cgi_lib.HtmlBot());
		}
	}
}
