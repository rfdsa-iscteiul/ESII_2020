package requisito4;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Requisito4 {

	private static Git git;
	private static String gitUrl = "https://github.com/vbasto-iscte/ESII1920.git";
	private static String repoPath = "src/main/resources";
	private static String fileToSearch = "covid19spreading.rdf";
	private static Repository repo;
	private static List<Ref> tags;
	private static Document doc;

	/**
	 * Clone the git repository Then will save the base html file into a Document
	 * object, check all the tags that exist on the git repository and finally write
	 * the final html file that will be shown on browser
	 * 
	 */
	public static void setup() {
		cloneRep();
		try {
			git = Git.open(new File(repoPath));
			repo = git.getRepository();
			doc = Jsoup.parse(new File("webContent.html"), "utf-8");
			checkTags();
			writeHtml();
		} catch (IOException | GitAPIException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Writes an html file called "web.html" with the html code that will populate
	 * the table
	 * 
	 * @throws IOException
	 */
	public static void writeHtml() throws IOException {
		File f = new File("web.html");
		f.createNewFile();
		try (PrintWriter pw = new PrintWriter(f, "utf-8")) {
			pw.write(doc.toString());
		}

	}

	/**
	 * If there isn't any file in the directory it will
	 * Clone a github repository from the String gitUrl to the path String repoPath
	 * else, will do nothing
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
	 * Gets a list of all tags on git repository, checksout for each tag
	 * 
	 * 
	 * @throws GitAPIException
	 */
	public static void checkTags() throws GitAPIException {
		tags = git.tagList().call();
		for (Ref r : tags) {
			git.checkout().setName(r.getName()).call();
			try {
				accessFile(r);
			} catch (RevisionSyntaxException | IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 
	 * Given a Ref tag, this method will search for a specific file associated with
	 * it and process the html code to populate the table
	 * 
	 * @param tag
	 * @throws RevisionSyntaxException
	 * @throws AmbiguousObjectException
	 * @throws IncorrectObjectTypeException
	 * @throws IOException
	 */
	public static void accessFile(Ref tag)
			throws RevisionSyntaxException, AmbiguousObjectException, IncorrectObjectTypeException, IOException {
		ObjectId commitId = repo.resolve(Constants.HEAD);
		try (RevWalk revWalk = new RevWalk(repo)) {
			RevCommit commit = revWalk.parseCommit(commitId);
			RevTree tree = commit.getTree();
			try (TreeWalk treeWalk = new TreeWalk(repo)) {
				treeWalk.addTree(tree);
				treeWalk.setRecursive(true);
				treeWalk.setFilter(PathFilter.create(fileToSearch));
				if (!treeWalk.next())
					throw new IllegalStateException("Did not find file");
				LocalDateTime date = commit.getAuthorIdent().getWhen().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDateTime().minusHours(1);
				String description = commit.getShortMessage();
				String link = "http://visualdataweb.de/webvowl/#iri=https://github.com/vbasto-iscte/ESII1920/raw/"
						+ tag.getName().split("/")[2] + "/covid19spreading.rdf";
				processHtml(tag, date, description, link);
			}
			revWalk.dispose();
		}
	}

	/**
	 * Given all those parameters, will write the html code to populate the table
	 * 
	 * @param tag
	 * @param commitTime
	 * @param description
	 * @param link
	 */
	public static void processHtml(Ref tag, LocalDateTime commitTime, String description, String link) {
		Element tbody = doc.select("tbody").first();
		tbody.append("<tr>\r\n" + "            		<td> "
				+ commitTime.format(DateTimeFormatter.ofPattern("HH:mm:ss EEE, dd-MM-yyyy")) + " </td>\r\n"
				+ "            		<td>" + fileToSearch + "</td>\r\n" + "            		<td> "
				+ tag.getName().split("/")[2] + " </td>\r\n" + "            		<td> " + description + "</td>\r\n"
				+ "            		<td> " + "<a href=\"" + link + "\"> Link</a>" + "</td>\r\n" + "            	</tr>");
	}

	public static void main(String[] args) {
		setup();
	}

}
