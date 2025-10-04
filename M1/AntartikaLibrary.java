class Chapter {
    String title;
    Chapter next, prev;

    Chapter(String title) {
        this.title = title;
        this.next = this.prev = null;
    }
}

class Book {
    String title;
    String author;
    Chapter headChapter;
    Book next; // Circular pointer

    Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.headChapter = null;
        this.next = null;
    }

    void addChapter(String title) {
        Chapter newChap = new Chapter(title);
        if (headChapter == null) {
            headChapter = newChap;
            return;
        }
        Chapter temp = headChapter;
        while (temp.next != null)
            temp = temp.next;
        temp.next = newChap;
        newChap.prev = temp;
    }

    void swapChapters(String c1, String c2) {
        Chapter first = headChapter, second = headChapter;
        while (first != null && !first.title.equals(c1)) first = first.next;
        while (second != null && !second.title.equals(c2)) second = second.next;

        if (first == null || second == null) return;

        String temp = first.title;
        first.title = second.title;
        second.title = temp;
    }

    void showChapters() {
        Chapter temp = headChapter;
        int i = 1;
        while (temp != null) {
            System.out.println("    ~ Bab " + i + ": " + temp.title);
            temp = temp.next;
            i++;
        }
    }
}

class Category {
    String name;
    Category prev, next;
    Book headBook;

    Category(String name) {
        this.name = name;
        this.prev = this.next = null;
        this.headBook = null;
    }

    void addBook(String title, String author) {
        Book newBook = new Book(title, author);
        if (headBook == null) {
            headBook = newBook;
            newBook.next = newBook;
            return;
        }
        Book temp = headBook;
        while (temp.next != headBook)
            temp = temp.next;
        temp.next = newBook;
        newBook.next = headBook;
    }

    void showBooks() {
        if (headBook == null) return;
        Book temp = headBook;
        do {
            System.out.println("  - " + temp.title + " | " + temp.author);
            temp.showChapters();
            temp = temp.next;
        } while (temp != headBook);
    }
}

class Shelf {
    String name;
    Shelf next;
    Category headCategory;

    Shelf(String name) {
        this.name = name;
        this.next = null;
        this.headCategory = null;
    }

    void addCategory(String name) {
        Category newCat = new Category(name);
        if (headCategory == null) {
            headCategory = newCat;
            return;
        }
        Category temp = headCategory;
        while (temp.next != null)
            temp = temp.next;
        temp.next = newCat;
        newCat.prev = temp;
    }

    void showAll() {
        Category cat = headCategory;
        while (cat != null) {
            System.out.println("Kategori: " + cat.name);
            cat.showBooks();
            cat = cat.next;
            System.out.println();
        }
    }
}

public class AntartikaLibrary {
    public static void main(String[] args) {
        Shelf shelf1 = new Shelf("Rak Fiksi");

        // Tambah kategori
        shelf1.addCategory("Novel");
        shelf1.addCategory("Komik");

        // Tambah buku Novel
        shelf1.headCategory.addBook("Laskar Pelangi", "Andrea Hirata");
        shelf1.headCategory.addBook("Negeri 5 Menara", "A. Fuadi");

        // Tambah bab ke Laskar Pelangi
        Book laskar = shelf1.headCategory.headBook;
        laskar.addChapter("Awal Cerita");
        laskar.addChapter("Sekolah Muhammadiyah");
        laskar.addChapter("Persahabatan");

        // Tambah bab ke Negeri 5 Menara
        Book menara = laskar.next;
        menara.addChapter("Pondok Madani");
        menara.addChapter("Man Jadda Wajada");

        // Tambah buku Komik
        shelf1.headCategory.next.addBook("Naruto", "Masashi Kishimoto");
        shelf1.headCategory.next.addBook("One Piece", "Eiichiro Oda");

        // Tambah bab ke Naruto
        Book naruto = shelf1.headCategory.next.headBook;
        naruto.addChapter("Ninja Akademi");
        naruto.addChapter("Ujian Chuunin");
        naruto.addChapter("Pertarungan Sannin");

        // Tambah bab ke One Piece
        Book onepiece = naruto.next;
        onepiece.addChapter("Petualangan Dimulai");
        onepiece.addChapter("East Blue");
        onepiece.addChapter("Grand Line");

        // Tampilkan output Sebelum Swap
        System.out.println("==Antartika Library==");
        System.out.println("== Sebelum Swap ==");
        System.out.println("Rak: " + shelf1.name);
        shelf1.showAll();

        // Lakukan Swap pada Laskar Pelangi (Bab 1 dan Bab 3)
        laskar.swapChapters("Awal Cerita", "Persahabatan");

        // Tampilkan output Setelah Swap
        System.out.println("Bab \"Bab 1: Awal Cerita\" ditukar dengan \"Bab 3: Persahabatan\"");
        System.out.println("=== Setelah Swap (hanya Laskar Pelangi) ===");
        System.out.println("Rak: " + shelf1.name);
        System.out.println("Kategori: Novel");

        // Cetak ulang hanya Novel
        Book temp = shelf1.headCategory.headBook;
        do {
            System.out.println("  - " + temp.title + " | " + temp.author);
            temp.showChapters();
            temp = temp.next;
        } while (temp != shelf1.headCategory.headBook);

        // Cetak Komik juga
        System.out.println("Kategori: Komik");
        Book komik = shelf1.headCategory.next.headBook;
        do {
            System.out.println("  - " + komik.title + " | " + komik.author);
            komik.showChapters();
            komik = komik.next;
        } while (komik != shelf1.headCategory.next.headBook);
    }
}
