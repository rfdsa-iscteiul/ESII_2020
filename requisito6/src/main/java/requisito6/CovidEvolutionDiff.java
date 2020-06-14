package requisito6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author r.dinis
 *
 */
/**
 * @author r.dinis
 *
 */
public class CovidEvolutionDiff {
	private static final int LAST_2_TAGS=2;

	private static Git git;
	private static String gitUrl;


	private static String repoPath;
	private static String fileToSearch;
	private static Repository repo;
	private static List<Ref> tags;
	private static List<String> tags_name,file_lines;
	private static BufferedReader[] buffer;
	private static  Document doc;

	public CovidEvolutionDiff() {
		run();
	}

	/**
	 * Metodo utilizado para chamar os metodos makeGitUrl, initiate, setup, writtefile e processHtml. 
	 */
	private void run() {
		makeGitUrl();

		initiate();

		setup();

		writtefile();

		processHtml();
	}

	/**
	 * Metodo utilizado para chamar os metodos  initiate, setup, writtefile e processHtml. 
	 * @return 1
	 */
	public int runTest() {
		initiate();

		setup();

		writtefile();

		processHtml();
		return 1;
	}

	/**
	 * Recebe e altera os seguintes parametros.
	 * @param gitUrl 
	 * @param repoPath
	 * @param fileToSearch
	 */
	public void setGitUrl(String gitUrl,String repoPath,String fileToSearch) {
		this.gitUrl = gitUrl;
		this.repoPath = repoPath;
		this.fileToSearch = fileToSearch;
	}

	/**
	 * Altera as variaveis gitUrl,repoPath,fileToSearch para os valores desejados
	 */
	private void makeGitUrl() {
		gitUrl = "https://github.com/vbasto-iscte/ESII1920.git";
		repoPath = "src/main/resources";
		fileToSearch = "covid19spreading.rdf";
	}

	/**
	 * Inicia variaveis
	 */
	private void initiate() {
		tags = new ArrayList<Ref>();
		tags_name = new ArrayList<>();
		file_lines = new ArrayList<>();
		buffer= new BufferedReader[2];
		try {
			doc = Jsoup.parse(new File("web.html"), "utf-8");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Chama o cloneRep, writeHtml e checkTags
	 */
	public void setup() {
		cloneRep();
		try {
			git = Git.open(new File(repoPath));
			repo = git.getRepository();
			checkTags();
			writeHtml();
		} catch (IOException | GitAPIException e) {
			e.printStackTrace();
		}
	}

	/* 
	* Grava um arquivo html chamado "web.html" com o código html que preencherá a tabela
	* @throws IOException
	*/
	public  void writeHtml() throws IOException {
		File f = new File("web2.html");
		f.createNewFile();
		try (PrintWriter pw = new PrintWriter(f, "utf-8")) {
			pw.append(doc.toString());
		}

	}

	/* 
	 * Se não houver nenhum arquivo no diretório, ele clonará um repositório do github da String gitUrl para o caminho String repoPath else, não fará nada
	* 
	*/
	public static void cloneRep() {
		if (new File(repoPath).list().length == 0) {
			try {
				git = Git.cloneRepository().setURI(gitUrl).setDirectory(new File(repoPath)).call();
				repo = git.getRepository();
			} catch (IllegalStateException | GitAPIException e) {
				e.printStackTrace();
			}
		}
	}
	


	/**
	 * Adiciona as 2 ultimas tags a um array.
	 * @throws GitAPIException
	 */
	private void checkTags() throws GitAPIException {
		tags = git.tagList().call();
		Ref r;
		for (int i = LAST_2_TAGS; i !=0 ;i--) {
			r = tags.get(tags.size()-i);
			git.checkout().setName(r.getName()).call();
			repo = git.getRepository();
			try {
				accessFile(r);
			} catch (RevisionSyntaxException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Utiliza as duas ultimas tags para ir buscar os dois ultimos ficheiros.
	 * 
	 * @param tag
	 * @throws RevisionSyntaxException
	 * @throws AmbiguousObjectException
	 * @throws IncorrectObjectTypeException
	 * @throws IOException
	 */
	private void accessFile(Ref tag)
			throws RevisionSyntaxException, AmbiguousObjectException, IncorrectObjectTypeException, IOException {
		tags_name.add(tag.getName().split("/")[2]);

		ObjectId commitId = repo.resolve(Constants.HEAD);
		try (RevWalk revWalk = new RevWalk(repo)) {
			RevCommit commit = revWalk.parseCommit(commitId);
			RevTree tree = commit.getTree();
			try (TreeWalk treeWalk = new TreeWalk(repo)) {
				treeWalk.addTree(tree);
				treeWalk.setRecursive(true);
				treeWalk.setFilter(PathFilter.create(fileToSearch));
				if (!treeWalk.next()) {
					throw new IllegalStateException("Did not find file");
				}

				ObjectId objectId = treeWalk.getObjectId(0);
				ObjectLoader loader = repo.open(objectId);

				try {
					String path = repoPath + File.separator + tag.getName().split("/")[2] + ".txt";
					File f = new File(path);
					f.getParentFile().mkdir();
					f.createNewFile();
					loader.copyTo(new FileOutputStream(f));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			revWalk.dispose();
		}
	}



	/**
	 * Chama o metodo openFiles.
	 */
	public void writtefile() {
		try {
			openFiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Le dois ficheiros texto
	 * @throws IOException
	 */
	public void openFiles() throws IOException {
		buffer[0] = initiate_read_file(tags_name.get(0));
		buffer[1] = initiate_read_file(tags_name.get(1));
		set_table_name();



		String line_1,line_2,save_line_1,save_line_2;

		while((line_1 = buffer[0].readLine()) != null && (line_2 = buffer[1].readLine()) != null){
			if(line_1.contains("<owl:NamedIndividual rdf:about=\"&untitled-ontology-3;")) {
				save_line_1 = 1 + line_1;
				save_line_2 = 2 + line_2;

				for(int i = 0; i!=6 ;i++) {
					save_line_1 +=buffer[0].readLine();
					save_line_2 +=buffer[1].readLine();
				}
				file_lines.add(save_line_1);
				file_lines.add(save_line_2);

			}
		}
	}
	/**
	 * Escreve no Document doc o tituo da tabela a que corresponde
	 * @throws FileNotFoundException
	 */
	private void set_table_name() throws FileNotFoundException {
		Element h_a = doc.select("h4").first();
		Element h_b = doc.select("h4").last();

		buffer[0] = initiate_read_file(tags_name.get(0));
		h_a.append(tags_name.get(0));
		buffer[1] = initiate_read_file(tags_name.get(1));
		h_b.append(tags_name.get(1));		
	}
	/**
	 * Retorna um BufferedReader associado a uma string
	 * @param tag_name
	 * @return BufferedReader objeto
	 * @throws FileNotFoundException
	 */
	public static BufferedReader initiate_read_file(String tag_name) throws FileNotFoundException  {
		String path = repoPath + File.separator + tag_name + ".txt";
		File file = new File(path);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		return br;
	}

	/**
	 * Le dois ficheitos texto.
	 * Retira os valores desejados do ficheiro e utiliza o objeto, Element tbody_first e tbody_last para escrever valores num objeto Document.
	 * No final chama o metodo writteHtml()
	 */
	private  void processHtml() {
		Element tbody_first = doc.select("tbody").first();
		Element tbody_last = doc.select("tbody").last();
		String regiao_a="",regiao_b="",internamentos_a="",internamentos_b="",infecoes_a="",infecoes_b="",testes_a="",testes_b="",
				to_return_testes_a="",to_return_internamento_a="",to_return_infecoes_a="",to_return_testes_b="",to_return_internamento_b="",to_return_infecoes_b="";

		String bold_text = "style=\"background-color:#FF0000;\"";

		for(int i = 0; i!= file_lines.size(); i+=2) {
			regiao_a = "<td>"+ regiao(file_lines.get(i))+ "</td>\r\n";
			regiao_b = "<td>"+regiao(file_lines.get(i+1))+"</td>\r\n";

			internamentos_a = internamento(file_lines.get(i));
			internamentos_b = internamento(file_lines.get(i+1));

			if(internamentos_a != internamentos_b) {
				to_return_internamento_a="<td "+bold_text+"> " + internamentos_a + " </td>\r\n";
				to_return_internamento_b="<td "+bold_text+"> " + internamentos_b + " </td>\r\n";
			}
			else {
				to_return_internamento_a="<td> " + internamentos_a + " </td>\r\n";
				to_return_internamento_b="<td> " + internamentos_b + " </td>\r\n";
			}
			infecoes_a = infecao(file_lines.get(i));
			infecoes_b = infecao(file_lines.get(i+1));

			if(infecoes_a != infecoes_b) {
				to_return_infecoes_a="<td "+bold_text+"> " + infecoes_a + " </td>\r\n";
				to_return_infecoes_b="<td "+bold_text+"> " + infecoes_b + " </td>\r\n";
			}
			else {
				to_return_infecoes_a="<td> " + infecoes_a + " </td>\r\n";
				to_return_infecoes_b="<td> " + infecoes_b + " </td>\r\n";
			}

			testes_a = teste(file_lines.get(i));
			testes_b = teste(file_lines.get(i+1));

			if(testes_b != testes_a) {
				to_return_testes_a="<td "+bold_text+"> " + testes_a + " </td>\r\n";
				to_return_testes_b="<td "+bold_text+"> " + testes_b + " </td>\r\n";
			}
			else {
				to_return_testes_a="<td> " + testes_a + " </td>\r\n";
				to_return_testes_b="<td> " + testes_b + " </td>\r\n";
			}
			tbody_first.append("<tr>\r\n" + regiao_a + to_return_infecoes_a + to_return_internamento_a + to_return_testes_a +"</tr>");
			tbody_last.append("<tr>\r\n" + regiao_b + to_return_infecoes_b + to_return_internamento_b + to_return_testes_b +"</tr>");

		}
		try {
			writeHtml();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * Divide a String para ter um valor inteiro segundo um formato
	 * Se o formato estiver errado retorna "X"
	 * @param line
	 * @return String com valor inteiro ou "X"
	 */
	private  String regiao(String line){
		try {
			return line.split(";")[1].split("\"")[0];
		}
		catch(Exception e){

		}
		return "X";
	}
	/**
	 * Divide a String para ter um valor inteiro segundo um formato
	 * Se o formato estiver errado retorna "X"
	 * @param line
	 * @return String com valor inteiro ou "X"
	 */
	private String infecao(String line){
		try {
			return line.split("<Infecoes rdf:datatype=\"&xsd;integer\">")[1].split("</Infecoes>")[0];
		}
		catch(Exception e){

		}
		return "X";
	}
	/**
	 * Divide a String para ter um valor inteiro segundo um formato
	 * Se o formato estiver errado retorna "X"
	 * @param line
	 * @return String com valor inteiro ou "X"
	 */
	private String internamento(String line){
		try {
			return line.split("<Internamentos rdf:datatype=\"&xsd;integer\">")[1].split("</Internamentos>")[0];
		}
		catch(Exception e){

		}
		return "X";
	}
	/**
	 * Divide a String para ter um valor inteiro segundo um formato
	 * Se o formato estiver errado retorna "X"
	 * @param line
	 * @return String com valor inteiro ou "X"
	 */
	private String teste(String line){
		try {
			return line.split("<Testes rdf:datatype=\"&xsd;integer\">")[1].split("</Testes>")[0];
		}
		catch(Exception e){
		}
		return "X";
	}


	/**
	 * Retorna o valor da String gitUrl
	 * @return String gitUrl
	 */
	public static String getGitUrl() {
		return gitUrl;
	}
	public static void main(String[] args) {
		CovidEvolutionDiff a = new CovidEvolutionDiff();
	}

}
