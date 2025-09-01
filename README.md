Założenia: 
 - Utworzona aplikacja jest mikroserwisem w wiekszym systemie. 
Inny mikroserwis po otrzymaniu SMS wywołuje utworzony serwis w celu sprawdzenia czy sms nie jest phishingiem.
Mozna w latwy sposob zamienic komunikacje pomiedzy uslugami z synchronicznej REST na asynchroniczna (np. kafka).
 - Jezeli uzytkownik wysle SMS Start/Stop na numer umozliwiajacy zarzadzanie subskrypcjami,
automatycznie zostaje dodany do bazy subskrybentow 
 - Podczas skalowania aplikacji do wiecej niz 1 instancji, warto rozwazyc uzycie zewnetrznego systemu cache 
(np. Redis) do przechowywania informacji o subskrybentach
 - Otrzymane numery SMS nadawcy i odbiorcy sa poprawne
 - Nalezy dodac API KEY do webRisk w application.yaml

TODO:
- Dopisac wiecej testow automatycznych do klas z pakietow adapters oraz infrastructure
- Ulepszyc testy automatyczne, dodac parametryzację 
- Dopisac szersze testy (integracyjne)
- Zabezpieczenie endpointu np. JWT
- Mozliwe ze warto dodac ponawianie wysylki wiadomosci do webRisk w przypadku otrzymania blednej odpowiedzi spodowanej
np. chwilowa niedostepnoscia