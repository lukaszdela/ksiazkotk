package lukks.eu.ksiazkotk;

import lukks.eu.ksiazkotk.model.Book;
import lukks.eu.ksiazkotk.model.BookStatus;
import lukks.eu.ksiazkotk.model.User;
import lukks.eu.ksiazkotk.repository.BookRepository;
import lukks.eu.ksiazkotk.repository.UserRepository;
import lukks.eu.ksiazkotk.service.IBookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class KsiazkotkApplication {

	public static void main(String[] args) {
		SpringApplication.run(KsiazkotkApplication.class, args);
	}



	@Bean
	public CommandLineRunner commandLineRunner(BookRepository bookRepository, UserRepository userRepository){
		return args -> {
			User jkowalski = User.builder().name("Jan").surname("Kowalski").login("jkowalski").email("jkowalski@ksiazko.tk").password("jkowalski").avatar("/avatars/128_16.png").build();
			User mnowak = User.builder().name("Marek").surname("Nowak").login("mnowak").email("mnowak@ksiazko.tk").password("mnowak").avatar("/avatars/128_2.png").build();
			User azaradna = User.builder().name("Anna").surname("Zaradna").login("azaradna").email("azaradna@ksiazko.tk").password("azaradna").avatar("/avatars/128_6.png").build();
			User pwozniak = User.builder().name("Paulina").surname("Wozniak").login("pwozniak").email("pwozniak@ksiazko.tk").password("pwozniak").avatar("/avatars/128_9.png").build();

			Book book1 = Book.builder().title("Karaluchy").author("Jo Nesbo").cover("/covers/j_karaluch.jpg").status(BookStatus.FREE).build();
			Book book2 = Book.builder().title("Pentagram").author("Jo Nesbo").cover("/covers/j_pentagram.jpg").status(BookStatus.FREE).build();
			Book book3 = Book.builder().title("Bazar zlych snow").author("Stephen King").cover("/covers/s_bazar.jpg").status(BookStatus.FREE).build();
			Book book4 = Book.builder().title("Zielona mila").author("Stephen King").cover("/covers/s_zmila.jpg").status(BookStatus.FREE).build();
			Book book5 = Book.builder().title("Polnoc w ogrodzie dobra i zla").author("John Berendt").cover("/covers/jb_polnoc.jpg").status(BookStatus.FREE).build();
			Book book6 = Book.builder().title("Noc w bibliotece").author("Agata Christie").cover("/covers/ac_nocbibl.jpg").status(BookStatus.FREE).build();
			Book book7 = Book.builder().title("Sztorm").author("Clive Cussler").cover("/covers/cc_sztorm.jpg").status(BookStatus.FREE).build();
			Book book9 = Book.builder().title("Harda").author("Elzbieta Cherezinska").cover("/covers/ech_harda.jpg").status(BookStatus.FREE).build();
			Book book10 = Book.builder().title("Krolowa").author("Elzbieta Cherezinska").cover("/covers/ec_krolowa.jpg").status(BookStatus.FREE).build();
			Book book11 = Book.builder().title("Cmentarz w Pradze").author("Umberto Eco").cover("/covers/ue_cemetary.jpg").status(BookStatus.FREE).build();
			Book book12 = Book.builder().title("Wilk z Wall Street").author("Jordan Belfort").cover("/covers/jb_wilk.jpg").status(BookStatus.FREE).build();
			Book book13 = Book.builder().title("Drood").author("Dan Simmons").cover("/covers/ds_drood.jpg").status(BookStatus.FREE).build();
			Book book14 = Book.builder().title("Dyskretny urok Fernet Branca").author("James Hamilton-Paterson").cover("/covers/dysk_urok.jpg").status(BookStatus.FREE).build();
			Book book15 = Book.builder().title("Zemsta Manitou").author("Graham Masterton").cover("/covers/gm_zmestmani.jpg").status(BookStatus.FREE).build();
			Book book16 = Book.builder().title("Klient").author("John Grisham").cover("/covers/jg_klient.jpg").status(BookStatus.FREE).build();
			Book book17 = Book.builder().title("Dziewczyna z pociagu").author("Paula Hawkins").cover("/covers/ph_dziewczyna.jpg").status(BookStatus.FREE).build();
			Book book18 = Book.builder().title("Sklepik z marzeniami").author("Stephen King").cover("/covers/sk_sklepik.jpg").status(BookStatus.FREE).build();
			Book book19 = Book.builder().title("Planeta Wygnania").author("Ursula K. Le Guin").cover("/covers/uk_plane.jpg").status(BookStatus.FREE).build();
			Book book20 = Book.builder().title("Czlowiek w labiryncie").author("Robert Silverberg").cover("/covers/rs_czlolab.jpg").status(BookStatus.FREE).build();
			Book book21 = Book.builder().title("Inferno").author("Dan Brown").cover("/covers/db_inferno.jpg").status(BookStatus.FREE).build();
			Book book22 = Book.builder().title("Rytual‚ - Tom 1").author("Dusan Fabian").cover("/covers/df_rytual.jpg").status(BookStatus.FREE).build();
			Book book23 = Book.builder().title("Dom zbrodni").author("Agata Christie").cover("/covers/ac_domzbro.jpg").status(BookStatus.FREE).build();
			Book book24 = Book.builder().title("Mistrz i Malgorzata").author("Michail Bulhakow").cover("/covers/ab_mistrz.jpg").status(BookStatus.FREE).build();


			List<Book> books1 = Arrays.asList(book1, book2, book3, book4, book5, book6);
			List<Book> books2 = Arrays.asList(book7, book9, book10, book11, book12);
			List<Book> books3 = Arrays.asList(book13, book14, book15, book16, book17, book18);
			List<Book> books4 = Arrays.asList(book19, book20, book21, book22, book23, book24);
			bookRepository.saveAll(books1);
			bookRepository.saveAll(books2);
			bookRepository.saveAll(books3);
			bookRepository.saveAll(books4);
			userRepository.save(jkowalski);
			userRepository.save(mnowak);
			userRepository.save(azaradna);
			userRepository.save(pwozniak);

			book1.setOwner(jkowalski);
			book2.setOwner(jkowalski);
			book3.setOwner(jkowalski);
			book4.setOwner(jkowalski);
			book5.setOwner(jkowalski);
			book6.setOwner(jkowalski);

			book7.setOwner(mnowak);
			book9.setOwner(mnowak);
			book10.setOwner(mnowak);
			book11.setOwner(mnowak);
			book12.setOwner(mnowak);

			book13.setOwner(azaradna);
			book14.setOwner(azaradna);
			book15.setOwner(azaradna);
			book16.setOwner(azaradna);
			book17.setOwner(azaradna);
			book18.setOwner(azaradna);

			book19.setOwner(pwozniak);
			book20.setOwner(pwozniak);
			book21.setOwner(pwozniak);
			book22.setOwner(pwozniak);
			book23.setOwner(pwozniak);
			book24.setOwner(pwozniak);

			jkowalski.setBooks(books1);
			mnowak.setBooks(books2);
			azaradna.setBooks(books3);
			pwozniak.setBooks(books4);

			userRepository.save(jkowalski);
			userRepository.save(mnowak);
			userRepository.save(azaradna);
			userRepository.save(pwozniak);
			bookRepository.saveAll(books1);
			bookRepository.saveAll(books2);
			bookRepository.saveAll(books3);
			bookRepository.saveAll(books4);

		};
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
