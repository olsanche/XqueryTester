import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;

import net.sf.saxon.s9api.*;

public class XqueryExecutor {

	public static void execute() throws FileNotFoundException, XQException {
		InputStream inputStream = new FileInputStream(new File("courses.xqy"));
		XQDataSource ds = new net.sf.saxon.xqj.SaxonXQDataSource();
		XQConnection conn = ds.getConnection();
		XQPreparedExpression exp = conn.prepareExpression(inputStream);
		XQResultSequence result = exp.executeQuery();
		while (result.next()) {
			System.out.println(result.getItemAsString(null));
		}
	}

	public static String execute(String xml, String xquery) throws IOException {
		try {
			Processor processor = new Processor(false);
			XQueryCompiler xqueryCompiler = processor.newXQueryCompiler();
			XQueryExecutable xqueryExecutable = xqueryCompiler.compile(new StringReader(xquery));
			XQueryEvaluator xqueryEvaluator = xqueryExecutable.load();

			DocumentBuilder documentBuilder = processor.newDocumentBuilder();
			XdmNode xmlDoc = documentBuilder.build(new StreamSource(new StringReader(xml)));

			// Utiliser XdmDestination pour convertir XdmNode en une source accept�e par
			// setSource
			XdmDestination destination = new XdmDestination();
			processor.writeXdmValue(xmlDoc, destination);

			// Convertir le XdmNode en Source
			Source source = destination.getXdmNode().asSource();

			xqueryEvaluator.setSource(source);

			XdmValue result = xqueryEvaluator.evaluate();

			return result.toString();
		} catch (SaxonApiException e) {
			e.printStackTrace();
		}

		return null;

	}
}
