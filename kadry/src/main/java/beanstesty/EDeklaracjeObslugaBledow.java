/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named

public class EDeklaracjeObslugaBledow {

    public static List<String> odpowiedznakodserwera(Integer kodserwera) {
        List<String> komunikaty = Collections.synchronizedList(new ArrayList<>());
        if (kodserwera!=null) {
            switch (kodserwera) {
                case 100: 
                    komunikaty.add("e");
                    komunikaty.add("Deklaracja nie została wysłana\n" +
                            "Usługi e-Deklaracje wykorzystują protokół wywoływania zdalnego dostępu do obiektów SOAP.\n"
                            + "Ten status wysyłki świadczy o wysłaniu do serwisu e-Deklaracje komunikatu niezgodnego ze standardem.");
                    break;
                case 101:
                    komunikaty.add("e");
                    komunikaty.add("Na skutek nieprzewidzianych trudności np. przerwanie połączenia nie udało się z sukcesem utrwalić w serwisie e-Deklaracje przesłanej deklaracji (dokumentu elektronicznego)");
                    
                case 102:
                    komunikaty.add("e");
                    komunikaty.add("Na skutek nieprzewidzianych trudności np. przerwanie połączenia nie udało się z sukcesem odpowiedzieć na żądanie UPO. W związku z powyższym sugerowane jest powtórzenie operacji – ponowne przesłanie żądania UPO.");
                    break;
                case 200:
                    komunikaty.add("i");
                    komunikaty.add("SUKCES! Dokument został przyjęty przez system e-Deklaracje i przekazany do Urzędu Skarbowego wskazanego w tym dokumencie.\n"
                            + "Weryfikacja dokumentu nie wykazała błędów.\n"
                            + "Dla dokumentu zostało wystawione Urzędowe Poświadczenie Odbioru (UPO).");
                    break;
                case 300:
                    komunikaty.add("w");
                    komunikaty.add("Brak dokumentu.\n"
                            + "W systemie e-Deklaracje nie został zarejestrowany dokument wskazany w przesłanym żądaniu \n"
                            + "(z podanym numerem referencyjnym). Deklaracja nie została wysłana.");
                    break;
                case 301:
                    komunikaty.add("i");
                    komunikaty.add("Dokument w trakcie przetwarzania, sprawdź wynik następnej weryfikacji dokumentu.\n" +
                            "Dokument został zapisany w systemie e-Deklaracje i oczekuje na weryfikację formalną.");
                    break;
                case 302:
                    komunikaty.add("i");
                    komunikaty.add("Dokument wstępnie przetworzony, sprawdź wynik następnej weryfikacji dokumentu\n" +
                            "Dokument przeszedł weryfikację formalną i oczekuje na ponowną weryfikację z listą CRL (unieważnionych certyfikatów). ");
                    break;
                case 303:
                    komunikaty.add("i");
                    komunikaty.add("Dokument w trakcie weryfikacji podpisu, sprawdź wynik następnej weryfikacji dokumentu\n" +
                            "Dokument w trakcie weryfikacji podpisu elektronicznego.");
                    break;
                case 400:
                    komunikaty.add("e");
                    komunikaty.add("Przetwarzanie dokumentu zakończone błędem\n" +
                            "Weryfikacja formalna dokumentu nie powiodła się - nie udało się jej poprawnie zakończyć");
                    break;
                case 401:
                    komunikaty.add("e");
                    komunikaty.add("Weryfikacja negatywna - dokument niezgodny ze schematem xsd\n" +
                             "Przesłany dokument nie jest zgodny z opisującym go schematem xsd, \n"
                            + "np. brak wypełnienia pozycji obowiązkowych, niewłaściwy format daty lub inny błąd wypełnienia deklaracji. ");
                    break;
                case 402:
                    komunikaty.add("e");
                    komunikaty.add("Brak aktualnego pełnomocnictwa/upoważnienia do podpisywania deklaracji\n" +
                             "Osoba składająca podpis elektroniczny pod przesłanym dokumentem nie ma stosownego pełnomocnictwa lub upoważnienia lub jest nieważne (UPL-1).");
                    break;
                case 403:
                    komunikaty.add("e");
                    komunikaty.add("Weryfikacja podpisu elektronicznego zakończyła się negatywnie wskutek np:\n" +
                             "niezgodności standardu użytego podpisu, \n"
                            + "użycia podpisu z nieobsługiwanym przez system e-Deklaracje rozszerzeniem krytycznym"
                            + "błędów w strukturze dokumentu uniemożliwiających wyodrębnienie podpisu elektronicznego");
                    break;
                case 404 :
                    komunikaty.add("e");
                    komunikaty.add("Dokument z nieważnym certyfikatem\n" +
                            "Certyfikat związany z kluczem prywatnym użytym do złożenia podpisu elektronicznego jest nieważny – minął okres jego ważności.\n" +
                            "Konieczne jest ponowne wysłanie Deklaracji z użyciem ważnego certyfikatu.\n");
                    break;
                case 408:
                    komunikaty.add("e");
                    komunikaty.add("Dokument zawiera błędy uniemożliwiające jego przetworzenie\n" +
                            "System e-Deklaracje podjął kilkukrotną próbę przeprowadzenia formalnej weryfikacji – zakończoną negatywnie.\n"
                            + "Na skutek pojawienia się błędu, którego system nie może zakwalifikować do znanej mu kategorii, \n"
                            + "dokument jest uznany za nieprawidłowy bez możliwości wskazania jednej przyczyny błędu.");
                    break;
                case 409:
                    komunikaty.add("e");
                    komunikaty.add(" Dokument zawiera niewłaściwą ilość i/lub rodzaj elementów\n" +
                            "Zła struktura dokumentu np. próba wysłania wielu deklaracji (paczki dokumentów) w jednym komunikacie.");
                    break;
                case 410:
                    komunikaty.add("e");
                    komunikaty.add(" Złożony dokument (zeznanie) bez podpisu nie może być korektą\n" +
                            "Dokument elektroniczny bez bezpiecznego podpisu elektronicznego weryfikowanego kwalifikowanym certyfikatem nie może być korektą.");
                    break;
                case 411:
                    komunikaty.add("e");
                    komunikaty.add("Weryfikacja negatywna – w systemie jest już złożony dokument (zeznanie) z takim identyfikatorem podatkowym");
                    break;
                case 412:
                    komunikaty.add("e");
                    komunikaty.add("Weryfikacja negatywna - niezgodność danych informacyjnych (np. niezgodność NIP, numeru PESEL, daty urodzenia, nazwiska,\n"
                            + " pierwszego imienia z danymi w zeznaniu)\n" +
                            "Błąd ten oznacza, że istnieje różnica między danymi osobowymi, jakie posiada system e-deklaracje,\n"
                            + " a tymi jakie zostały wpisane na składanej deklaracji.");
                    break;
                case 414:
                    komunikaty.add("e");
                    komunikaty.add("Weryfikacja negatywna - błąd danych identyfikacyjnych. \n"
                            + "Błąd 414 informuje, że na wysyłanej e-deklaracji wystąpił jeden z poniższych błędów w danych autoryzujących\n"
                            + "A) Błąd w danych identyfikacyjnych przenoszonych do sekcji danych autoryzujących w zeznaniu:\n"
                            + "B) W danych autoryzujących wpisana jest błędna kwota przychodu z zeznania lub rocznego \n"
                            + "obliczenia podatku za rok podatkowy o dwa lata wcześniejszy niż rok,\n"
                            + " w którym jest składany dokument elektroniczny. ");
                    break;
                case 418:
                    komunikaty.add("e");
                    komunikaty.add("Dla złożonej deklaracji wymagane jest użycie podpisu kwalifikowanego");
                    break;
                case 419:
                    komunikaty.add("e");
                    komunikaty.add("Brak zaznaczenia celu złożenia formularza jako korekty deklaracji (zeznania) lub brak uzasadnienia przyczyny złożenia korekty deklaracji");
                    break;
                case 422:
                    komunikaty.add("e");
                    komunikaty.add("Weryfikacja negatywna – dokument złożony z użyciem danych autoryzujących może złożyć wyłącznie podatnik, będący osobą fizyczną");
                    break;
                case 423:
                    komunikaty.add("e");
                    komunikaty.add("Dokument może złożyć wyłącznie podmiot będący osobą fizyczną, niebędący pełnomocnikiem");
                    break;
                case 440:
                    komunikaty.add("e");
                    komunikaty.add("Paczka/Dokument zbiorczy zawiera deklarację, która została już przesłana");
                    break;
                case 443:
                    komunikaty.add("e");
                    komunikaty.add(" Dokument znajduje się już w systemie");
                    break;
                case 452:
                    komunikaty.add("e");
                    komunikaty.add("Weryfikacja negatywna – nieobsługiwany typ deklaracji");
                    break;
                case 460:
                    komunikaty.add("e");
                    komunikaty.add("Nieprawidłowa struktura Numeru identyfikacyjnego VAT lub go brak");
                    break;
                case 466:
                    komunikaty.add("e");
                    komunikaty.add("Nieprawidłowe podsumowanie kwot");
                    break;
                default:
                    komunikaty.add("e");
                    komunikaty.add("Wystąpił błąd nieprzewidziany przez admina.");
                    break;

            }
        } else {
            komunikaty.add("e");
            komunikaty.add("Nieudane połączenie z serwerem!");
        }
        return komunikaty;
    }

}
