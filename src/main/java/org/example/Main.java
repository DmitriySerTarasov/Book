package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();

        // Добавление книг в список
        books.add(new Book("Как понять жизнь программиста", "Чокнутый проффесор", 2025));
        books.add(new Book("Записки сумасшедшего", "Тайный писатель", 1562));
        books.add(new Book("Война и мир", "Лев Толстой", 1828));

        // Сериализация списка книг в файл
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("books.dat"))) {
            oos.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Десериализация списка книг из файла
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("books.dat"))) {
            List<Book> loadedBooks = (List<Book>) ois.readObject();
            for (Book book : loadedBooks) {
                book.displayInfo();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Сортировка книг по году издания
        Collections.sort(books);

        // Вывод отсортированного списка книг
        System.out.println("\nОтсортированный список книг:");
        for (Book book : books) {
            book.displayInfo();
        }

        // Фильтрация книг, изданных после 1950 года
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getYear() > 1950) {
                filteredBooks.add(book);
            }
        }

        // Вывод отфильтрованного списка книг
        System.out.println("\nКниги, изданные после 1950 года:");
        for (Book book : filteredBooks) {
            book.displayInfo();
        }
    }

    public static class Book implements Comparable<Book> {
        private String title;
        private String author;
        private int year;

        public Book(String title, String author, int year) {
            setTitle(title);
            setAuthor(author);
            setYear(year);
        }

        public void setTitle(String title) {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Название не должно быть пустым");
            }
            this.title = title;
        }

        public void setAuthor(String author) {
            if (author == null || author.trim().isEmpty()) {
                throw new IllegalArgumentException("Автор не должен быть пустым");
            }
            this.author = author;
        }

        public void setYear(int year) {
            if (year <= 0) {
                throw new IllegalArgumentException("Год издания должен быть положительным числом.");
            }
            this.year = year;
        }

        public void displayInfo() {
            System.out.printf("Название: %s, Автор: %s, Год издания: %d%n", title, author, year);
        }

        public boolean isOld() {
            return (2026 - year) > 50;
        }

        public boolean isAntique() {
            return (2026 - year) > 100;
        }

        public String toFormattedString() {
            return String.format("%s (%s, %d)", title, author, year);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Book book = (Book) o;
            return year == book.year && title.equals(book.title) && author.equals(book.author);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, author, year);
        }

        @Override
        public int compareTo(Book other) {
            return Integer.compare(this.year, other.year);
        }

        public String getTitle() {
            return title;
        }

        public int getYear() {
            return year;
        }
    }
}
