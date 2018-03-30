import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

/**
 * The Volkan_Ozer program implements simple movie database application. Program
 * reads information (such as movie name, cast, date) from a movie database file
 * and then creates database application. Application contains list movies, find
 * movies, comparison by metascore, movies in three hours, create watch list,
 * display watch list sections.
 * 
 * @author Volkan Ozer
 * @version 1.0
 * @since 2017-11-26
 */
public class App {

	/**
	 * This is the main method which implements movie database application.
	 * 
	 * @param args
	 *            Unused
	 * @throws FileNotFoundException
	 *             when a file specified by a program cannot be found
	 */
	public static void main(String[] args) throws FileNotFoundException {

		// ArrayList to store movies
		ArrayList<Movie> movieDatabase = new ArrayList<>();

		// open a file and check if it is exists or not
		File file = new File("movies.txt");
		Scanner input = new Scanner(file);
		if (!file.exists()) {
			System.out.println("File does not exists");
		}

		// array to store movies that we read from the file
		int MAX_MOVIE_COUNT = 100;
		Movie[] movie = new Movie[MAX_MOVIE_COUNT];

		// reading file
		int i = 0;
		while (input.hasNext()) {

			movie[i] = new Movie(null, null);

			// reads first line
			input.next();
			String strLine = input.nextLine();
			movie[i].name = strLine.trim();

			// reads second line
			input.next();
			strLine = input.nextLine();
			movie[i].director = strLine.trim();

			// reads third line
			input.next();
			strLine = input.nextLine();
			String[] strCast = strLine.trim().split(", ");
			for (int k = 0; k < strCast.length; k++) {
				if (strCast[k] != null)
					movie[i].cast.add(strCast[k]);
			}

			// reads fourth file
			input.next();
			strLine = input.nextLine();
			String[] strType = strLine.trim().split(", ");
			for (int k = 0; k < strType.length; k++) {
				if (strType[k] != null)
					movie[i].type.add(strType[k]);
			}

			// reads fifth line
			// date array stores date (e.g. December 1, 2017) in a string
			input.next();
			strLine = input.nextLine();
			String[] strDate = strLine.trim().split(" ");
			String[] date = null;
			date = new String[strDate.length - 1];
			for (int k = 1; k < strDate.length; k++) {
				if (strDate[k] != null) {
					date[k - 1] = strDate[0] + " " + strDate[k] + ", 2017";
				}
			}

			// reads sixth line
			// hour array stores time (e.g. 13:00)
			// session array concatenate dates with hours
			input.next();
			strLine = input.nextLine();
			String[] strTime = strLine.trim().split(" ");
			String[] hour = new String[strTime.length];
			String[] session = new String[date.length * hour.length];
			for (int k = 0; k < strTime.length; k++) {
				if (strTime[k] != null) {
					hour[k] = strTime[k];
					for (int m = 0; m < date.length; m++) {
						for (int n = 0; n < hour.length; n++) {
							session[k] = date[m] + " " + hour[n];
						}
					}
				}
			}
			for (int m = 0; m < date.length; m++) {
				for (int n = 0; n < hour.length; n++) {
					session[m] = date[m] + " " + hour[n];
					SimpleDateFormat ft = new SimpleDateFormat("MMMM dd, yyy hh:mm", Locale.ENGLISH);
					try {
						movie[i].dates.add(ft.parse(session[m]));
					} catch (ParseException e) {
						System.out.println("Unparseable using " + ft);
					}
				}

			}

			// reads seventh line
			input.next();
			strLine = input.nextLine();
			movie[i].metascore = Integer.parseInt(strLine.trim());

			movieDatabase.add(movie[i]);
			i++;
		}
		input.close();

		Scanner scanner = new Scanner(System.in);
		String choiceQuit = "";
		String choiceBackToMenu = "";
		PrintWriter output = new PrintWriter("watchList.txt");
		ArrayList<Movie> watchList = new ArrayList<>();
		do {
			System.out.println("============================");
			System.out.println(
					"[1] List All Movies\n[2] Find Movie\n[3] Highest Rated Movies\n[4] Movies in Three Hours\n[5] Add to Watchlist\n[6] Display Watchlist\n[9] Movies with Highest Metascore to Lowest\n[q] Quit");
			System.out.println("============================");
			System.out.print("ENTER YOUR CHOICE > ");
			choiceQuit = scanner.next();
			switch (choiceQuit) {
			case "1":
				System.out.println("Listing movies\n");
				for (i = 0; i < movieDatabase.size(); i++) {
					System.out.println(movieDatabase.get(i).toString());
					System.out.println();
				}
				break;
			case "2":
				do {
					System.out.println("--------------------------");
					System.out.println(
							"[1] Find by name\n[2] Find by director\n[3] Find by cast\n[4] Find by type\n[b] Back to the Main Menu");
					System.out.println("--------------------------");
					choiceBackToMenu = scanner.next();
					switch (choiceBackToMenu) {
					case "1":
						int counter = 0;
						System.out.println("\nFind by movie name. Enter at least three characters.");
						System.out.print("ENTER MOVIE NAME: ");
						String name = scanner.next().toUpperCase();
						if (name.length() < 3)
							System.out.println("\nPlease enter at least three characters.");
						else {
							for (i = 0; i < movieDatabase.size(); i++) {
								for (int j = 0; j < movieDatabase.get(i).name.length() - name.length(); j++) {
									if (movieDatabase.get(i).name.toUpperCase().substring(j, j + name.length())
											.contains(name)) {
										System.out.println();
										System.out.println(movieDatabase.get(i).toString());
										System.out.println();
									} else
										counter++;
								}
								if (counter == movieDatabase.size()
										* (movieDatabase.get(i).name.length() - name.length()))
									System.out.println("\nNo movies found\n");
							}
						}
						break;
					case "2":
						counter = 0;
						System.out.println("Searching by director");
						System.out.print("ENTER DIRECTOR NAME: ");
						String director = scanner.next().toUpperCase();
						for (i = 0; i < movieDatabase.size(); i++) {
							for (int j = 0; j < movieDatabase.get(i).director.length() - director.length(); j++) {
								if (movieDatabase.get(i).director.toUpperCase().substring(j, j + director.length())
										.contains(director)) {
									System.out.println();
									System.out.println(movieDatabase.get(i).toString());
									System.out.println();
								} else
									counter++;
							}
							if (counter == movieDatabase.size()
									* (movieDatabase.get(i).director.length() - director.length()))
								System.out.println("\nNo directors found\n");
						}
						break;
					case "3":
						counter = 0;
						System.out.println("Searching by cast");
						System.out.print("ENTER CAST NAME: ");
						String cast = scanner.next().toUpperCase();
						for (i = 0; i < movieDatabase.size(); i++) {
							for (int k = 0; k < movieDatabase.get(i).cast.size(); k++) {
								for (int j = 0; j < movieDatabase.get(i).cast.get(k).length() - cast.length(); j++) {
									if (movieDatabase.get(i).cast.get(k).toUpperCase().substring(j, j + cast.length())
											.contains(cast)) {
										System.out.println();
										System.out.println(movieDatabase.get(i).toString());
										System.out.println();
									} else
										counter++;
								}
								if (counter == movieDatabase.get(i).cast.size()
										* (movieDatabase.get(i).cast.get(k).length() - cast.length()))
									System.out.println("\nNo casts found\n");
							}

						}
						break;
					case "4":
						counter = 0;
						System.out.println("Searching by type");
						System.out.print("ENTER TYPE NAME: ");
						String type = scanner.next().toUpperCase();
						for (i = 0; i < movieDatabase.size(); i++) {
							for (int j = 0; j < movieDatabase.get(i).type.size(); j++) {
								for (int k = 0; k < movieDatabase.get(i).type.get(j).length() - type.length(); k++) {
									if (movieDatabase.get(i).type.get(j).toUpperCase().substring(k, k + type.length())
											.contains(type)) {
										System.out.println();
										System.out.println(movieDatabase.get(i).toString());
										System.out.println();
									} else
										counter++;
								}
								if (counter == movieDatabase.get(i).type.size()
										* (movieDatabase.get(i).type.get(j).length() - type.length()))
									System.out.println("\nNo types found\n");
							}
						}
						break;
					}
					if (choiceBackToMenu.equals("b"))
						System.out.println("Returning back to the main menu\n");
				} while (!choiceBackToMenu.contains("b"));
				break;
			case "3":
				int counter = 0;
				System.out.println("Listing Highest Scored Movies\n");
				System.out.print("ENTER MINIMUM METASCORE: ");
				int minScore = scanner.nextInt();
				for (i = 0; i < movieDatabase.size(); i++) {
					if (movieDatabase.get(i).metascore >= minScore) {
						System.out.println();
						System.out.println(movieDatabase.get(i).toString());
						System.out.println();
					} else {
						counter++;
						if (counter == movieDatabase.size())
							System.out.println("\nNo movies to display with a metascore higher than " + minScore);
					}
				}
				break;
			case "4":
				System.out.println("\nMovies that starts in three hours:");
				Date currentTime = new Date();
				GregorianCalendar calender = new GregorianCalendar();
				Calendar temp = calender.getInstance();
				temp.setTime(currentTime);
				for (i = 0; i < movieDatabase.size(); i++) {
					GregorianCalendar[] dates = new GregorianCalendar[movieDatabase.get(i).dates.size()];
					for (int j = 0; j < movieDatabase.get(i).dates.size(); j++) {
						dates[j] = new GregorianCalendar();
						dates[j].setTime(movieDatabase.get(i).dates.get(j));
						boolean sameYear = temp.get(Calendar.YEAR) == dates[j].get(Calendar.YEAR);
						boolean sameMonth = temp.get(Calendar.MONTH) == dates[j].get(Calendar.MONTH);
						boolean sameDay = temp.get(Calendar.DAY_OF_MONTH) == dates[j].get(Calendar.DAY_OF_MONTH);
						int inHours = dates[j].get(Calendar.HOUR_OF_DAY) - temp.get(Calendar.HOUR_OF_DAY);
						int inMinutes = temp.get(Calendar.MINUTE) - dates[j].get(Calendar.MINUTE);
						int inThreeHours = 0;
						if (inMinutes < 0) {
							if (inHours >= 1) {
								inMinutes += 60;
								inThreeHours = (inHours - 1) * 60 + inMinutes;
							}
						} else if (inMinutes > 0) {
							if (inHours >= 1) {
								inMinutes *= -1;
								inThreeHours = (inHours) * 60 + inMinutes;
							}
						} else
							inThreeHours = inMinutes;
						if (sameDay && sameMonth && sameYear) {
							if (temp.get(Calendar.HOUR_OF_DAY) < dates[j].get(Calendar.HOUR_OF_DAY)) {
								if (inHours <= 3) {
									System.out.printf("%-40s %c %-1s%3c Starts in %2d minutes\n",
											movieDatabase.get(i).name, '>', dates[j].getTime(), '>', inThreeHours);

								}
							}
						}
					}
				}
				break;
			case "5":
				System.out.println("\nSelect from the list");
				for (i = 0; i < movieDatabase.size(); i++)
					System.out.println(i + ". " + movieDatabase.get(i).name);
				System.out.print("\nENTER SELECTION > ");
				int selection = scanner.nextInt();
				if (selection > 9)
					System.out.println("Invalid Movie Selection");
				else {
					watchList.add(movieDatabase.get(selection));
					System.out.println(movieDatabase.get(selection).name + " is added to your watchlist.");
				}
				output.println(movieDatabase.get(selection));
				output.println();
				output.flush();
				break;
			case "6":
				System.out.println("\nMovies in the WatchList:\n");
				for (i = 0; i < watchList.size(); i++) {
					System.out.println(watchList.get(i).toString());
					System.out.println();
				}
				break;
			case "9":
				System.out.println("\nPrinting movies in descending order (from highest metascore to lowest)\n");
				int[] metascore = new int[movieDatabase.size()];
				for (i = 0; i < movieDatabase.size(); i++)
					metascore[i] = movieDatabase.get(i).metascore;
				Arrays.sort(metascore);
				for (i = 0; i < metascore.length; i++)
					movieDatabase.get(i).metascore = metascore[i];
				for (i = movieDatabase.size() - 1; i > 0; i--) {
					System.out.println(movieDatabase.get(i).toString());
					System.out.println();
				}
				break;
			}
			if (!choiceQuit.equals("q")) {
				System.out.println("\nPress any key to continue");
				try {
					System.in.read();
				} catch (Exception e) {
				}
			}
		} while (!choiceQuit.contains("q"));
		scanner.close();
		output.close();
	}
}
