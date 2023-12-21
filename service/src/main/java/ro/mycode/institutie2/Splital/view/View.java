package ro.mycode.institutie2.Splital.view;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ro.mycode.institutie2.Splital.model.Spital;
import ro.mycode.institutie2.Splital.repository.SpitalRepo;

import java.util.Optional;
import java.util.Scanner;


@Service
public class View {

    private Scanner scanner;
    private SpitalRepo spitalRepo;


    public View(SpitalRepo spitalRepo) {
        this.spitalRepo = spitalRepo;
        this.scanner = new Scanner(System.in);
    }


    public void meniu() {
        System.out.println("Apasa tasta 1 pentru a vizualiza toate spitalele");
        System.out.println("Apasa tasta 2 pentru a adauga un spital");
        System.out.println("Apasat tasta 3 pentru a sterge un spital");
        System.out.println("Apasa tasta 4 pentru a modifica un spital");
    }

    public void play() {
        int aux = 0;
        boolean running = true;

        while (running) {
            meniu();
            aux = Integer.parseInt(scanner.nextLine());

            switch (aux) {
                case 1:
                    afisare();
                    break;
                case 2:
                    adaugareSpital();
                    break;
                case 3:
                    stergereSpital();
                    break;
                case 4:
                    updateSpital();
                    break;

                default:
                    System.out.println("Alegerea este gresita!");
            }
        }
    }


    public void afisare() {
        this.spitalRepo.findAll().forEach(spital -> {
            System.out.println(spital);
        });
    }


    @Transactional
    public void adaugareSpital() {
        System.out.println("Denumire: ");
        String denumire = scanner.nextLine();
        System.out.println("Adresa: ");
        String adresa = scanner.nextLine();
        System.out.println("Specializare");
        String specializare = scanner.nextLine();

        Spital spital = Spital.builder()
                .denumire(denumire)
                .adresa(adresa)
                .specializare(specializare)
                .build();

        spitalRepo.saveAndFlush(spital);
        System.out.println("Spitalul a fost adaugat cu succes");

    }

    @Transactional
    public void stergereSpital() {
        System.out.println("Introduceti denumirea si adresa");
        System.out.println("Denumire: ");
        String denumire = scanner.nextLine();
        System.out.println("Adresa: ");
        String adresa = scanner.nextLine();

        Optional<Spital> spital = spitalRepo.findSpitalByDenumireAndAdresa(denumire, adresa);
        if (spital.isPresent()) {
            spitalRepo.delete(spital.get());
            System.out.println("Spitalul a fost sters din baza de date");
        } else {
            System.out.println("Spitalul nu exista");
        }
    }

    @Transactional
    public void updateSpital() {
        System.out.println("Introduceti denumirea si adresa");
        System.out.println("Denumire: ");
        String denumire = scanner.nextLine();
        System.out.println("Adresa: ");
        String adresa = scanner.nextLine();
        Optional<Spital> spital = spitalRepo.findSpitalByDenumireAndAdresa(denumire, adresa);
        if (spital.isPresent()) {
            System.out.println("Introduceti specializarea noua: ");
            String specializare=scanner.nextLine();

            Spital spital1=spital.get();
            spital1.setSpecializare(specializare);

            spitalRepo.saveAndFlush(spital1);

            System.out.println("Specializarea spitalului a fost actualizata");
        }else{
            System.out.println("Spitalul nu exista in baza de date");
        }

    }


}
