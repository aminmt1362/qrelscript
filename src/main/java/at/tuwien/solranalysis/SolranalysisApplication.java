package at.tuwien.solranalysis;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.Data;

@SpringBootApplication
@EnableAutoConfiguration
public class SolranalysisApplication implements CommandLineRunner {
	
	public static String PDF2HTMLCORE = "pdf2html";
	public static String PDF2TABLECORE = "pdf2table";
	public static String PDFGENIECORE = "pdfgenie";
	
	public static void main(String[] args) {
		SpringApplication.run(SolranalysisApplication.class, args);
	}
	
	@Autowired
	private RelevanceCheck rcheck;

	@Override
	public void run(String... args) throws Exception {
		// Read Questions from text file
		String fileName = "/home/amin/Documents/amin/Evaluation/20170527/allq";
		String solr = "http://localhost:8983/solr/";
		String solrQueryHTML = "select?fl=id,score&q=text:*%s*&rows=20&wt=json";
		String solrQuery = "select?fl=id,score&q=content:*%s*&rows=20&wt=csv";
		List<String> lstQuestions = new ArrayList<>();

		//read file into stream, try-with-resources
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

			stream.forEach(file -> lstQuestions.add(file));
			

		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		StringBuilder sb = new StringBuilder();
		String pdf2htmlUrlString = "";
		String pdf2tableUrlString = "";
		String pdfgenieurlString = "";
		
		
		/*RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());    
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));
		HttpEntity<String> entity = new HttpEntity<String>(headers);*/
		
		SolrClient pdf2htmlclient = new HttpSolrClient.Builder(solr + PDF2HTMLCORE).build();
		SolrClient pdf2tableclient = new HttpSolrClient.Builder(solr + PDF2TABLECORE).build();
		SolrClient pdf2Genie = new HttpSolrClient.Builder(solr + PDFGENIECORE).build();
		
		// For each file send to Solr and receive answer
		String resultPDF2HTML = "";
		String resultPDF2TABLE = "";
		String resultPDFGenie = "";
		
		Integer rowCount = 20;
		
		 SolrQuery query = new SolrQuery();
		 Integer counter = 1;
		 
		 List<Qrel> pdf2Htmlqrels = new ArrayList<>();
		 BufferedWriter writer = new BufferedWriter(new FileWriter("/home/amin/Documents/amin/Evaluation/20170527/pdf2html.qrel"));
		 BufferedWriter writerpdf2table = new BufferedWriter(new FileWriter("/home/amin/Documents/amin/Evaluation/20170527/pdf2table.qrel"));
		 BufferedWriter writerpdfgenie = new BufferedWriter(new FileWriter("/home/amin/Documents/amin/Evaluation/20170527/pdfgenie.qrel"));
		for (String file : lstQuestions) {
			// Query for pdf2html
			query.setFields("id", "score");
			query.setQuery("text:*" + file + "*");
			query.setRows(rowCount);
			
			QueryResponse respPDF2HTML = pdf2htmlclient.query(query);
			SolrDocumentList results = respPDF2HTML.getResults();
			Qrel qrel = null;
			
			for (SolrDocument solrDocument : results) {
				qrel = new Qrel();
				qrel.setQueryId(counter.toString());
				qrel.setDocumentId(solrDocument.get("id").toString().replace("[", "").replace("]", ""));
				qrel.setScore(solrDocument.get("score").toString());
				qrel.setRelevance(rcheck.chechRelevance(file,solrDocument.get("id").toString().replace("[", "").replace("]", ""), CORETYPE.PDF2HTML).toString());
				writer.write(qrel.toFile());
				writer.newLine();
				pdf2Htmlqrels.add(qrel);
			}
			
			// PDF 2 TABLE
			query = new SolrQuery();
			query.setFields("documentid", "score");
			query.setQuery("content:*" + file + "*");
			query.setRows(rowCount);
			QueryResponse respPDF2TABLE = pdf2tableclient.query(query);
			results = respPDF2TABLE.getResults();
			qrel = null;
			
			for (SolrDocument solrDocument : results) {
				qrel = new Qrel();
				qrel.setQueryId(counter.toString());
				qrel.setDocumentId(solrDocument.get("documentid").toString().replace("[", "").replace("]", ""));
				qrel.setScore(solrDocument.get("score").toString());
				qrel.setRelevance(rcheck.chechRelevance(file,solrDocument.get("documentid").toString().replace("[", "").replace("]", ""), CORETYPE.PDF2HTML).toString());
				writerpdf2table.write(qrel.toFile());
				writerpdf2table.newLine();
				pdf2Htmlqrels.add(qrel);
			}
			

			// PDF GENIE
			query = new SolrQuery();
			query.setFields("documentid", "score");
			query.setQuery("content:*" + file + "*");
			query.setRows(rowCount);
			QueryResponse respPDFGENIE = pdf2Genie.query(query);
			results = respPDFGENIE.getResults();
			qrel = null;
			
			for (SolrDocument solrDocument : results) {
				qrel = new Qrel();
				qrel.setQueryId(counter.toString());
				qrel.setDocumentId(solrDocument.get("documentid").toString().replace("[", "").replace("]", ""));
				qrel.setScore(solrDocument.get("score").toString());
				qrel.setRelevance(rcheck.chechRelevance(file,solrDocument.get("documentid").toString().replace("[", "").replace("]", ""), CORETYPE.PDF2HTML).toString());
				writerpdfgenie.write(qrel.toFile());
				writerpdfgenie.newLine();
				pdf2Htmlqrels.add(qrel);
			}
			
			counter++;
			
		/*	pdf2htmlUrlString = sb.append(solr).append(PDF2HTMLCORE).append("/").append(q).toString();
			
			ResponseEntity<byte[]> resultPDF2HTML1 = restTemplate.exchange(pdf2htmlUrlString, HttpMethod.GET , entity, byte[].class);
			resultPDF2HTML = new String (resultPDF2HTML1.getBody());
			
			// Query for pdf2table
			pdf2tableUrlString = sb.append(solr).append(PDF2TABLECORE).append("/").append(String.format(solrQuery, file).replace(" ", "%20")).toString();
			resultPDF2TABLE += restTemplate.getForObject(pdf2tableUrlString, String.class);
			
			// Query for pdfgenie
			pdfgenieurlString = sb.append(solr).append(PDFGENIECORE).append("/").append(String.format(solrQuery, file).replace(" ", "%20")).toString();
			resultPDFGenie += restTemplate.getForObject(pdfgenieurlString, String.class);*/
		}
		writer.close();
		writerpdfgenie.close();
		writerpdf2table.close();
		
		
	}
	
	public enum CORETYPE {
		PDF2HTML, PDF2TABLE, PDFGENIE;
	}
	
	
	@Data
	public class Qrel {
		private String queryId;
		private String constant;
		private String documentId;
		private String relevance;
		private String score;
		
		public String toFile() {
			return String.format("%s %s %s %s", queryId, "0", documentId, score);
		}
	}
}
