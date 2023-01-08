import java.io.*;
import java.util.*;


import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class Main {
    public static void main(String[] args) throws IOException {

        List<String> booksList = new ArrayList<>();

        Map<Integer, String> authors = new HashMap<>();
        try (InputStream inputStream = new FileInputStream("author.csv");
             Scanner scanner = new Scanner(inputStream)) {
            String s1 = "";
            while (scanner.hasNextLine()) {
                s1 = scanner.nextLine();
                String[] partsOfAuthor = s1.split(",");

                authors.put(valueOf(partsOfAuthor[0]), partsOfAuthor[1]);
            } //Записали каждую пару id-name в наш Map authors

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(authors);

        String s2 = "";
        try (InputStream input = new FileInputStream("book.csv");
             Scanner scanner = new Scanner(input)) {
            while (scanner.hasNextLine()) {
                s2 = scanner.nextLine();
                String[] Arr = s2.split(",");
                booksList.add(Arr[0]);
                booksList.add(Arr[1]);
                booksList.add(Arr[2]);
                booksList.add(Arr[3]);
                booksList.add(Arr[4]);
                booksList.add(Arr[5]);
                //Создали коллекцию наших книг с полями id, title, price, amount, image_path,  author_id

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(booksList.get(25));

        String s3 = "";
        byte[] bytes = null;

        try (InputStream input = new FileInputStream("book.csv");
             Scanner scanner = new Scanner(input)) {
            while (scanner.hasNextLine()) {

                s3 = scanner.nextLine();

                String[] Arr = s3.split(",");
                String title = Arr[1]; //Считайте название книги (столбец title)
                double price = parseDouble(Arr[2]);
                String author_id = Arr[5]; //Считайте поле author_id
                String author_name = authors.get(parseInt(author_id)); //Из Map authors найдите имя автора по этому author_id
                String image_path = "images" + File.separator + Arr[4]; //Считайте название файла-обложки книги (поле image_path)
                try (FileInputStream inputStream = new FileInputStream(image_path)) {
                    bytes = inputStream.readAllBytes(); //Считайте картинку с этим названием (image_path), которая лежит в папке images в массив byte[]

                    FileOutputStream outputStream = new FileOutputStream("result/img" + File.separator + author_name + "–" + title + ".jpg");
                    {
                        //Запишите этот массив байт в новую картинку. Картинка должна лежать в папке result/img.
                        // Картинка должна иметь название вида «Имя автора – название книги»
                        outputStream.write(bytes);
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println(ex.getMessage());
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }

            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        //1.	Найдите самую дорогую книгу.  (поле price отвечает за цену)
        //2.	Найдите самую дешевую книгу.
        //3.	Запишите в текстовый файл результаты. Файл должен лежать в папке result

        Map<String, Double> prices = new HashMap<>();
        try (InputStream inputStream = new FileInputStream("book.csv");
             Scanner scanner = new Scanner(inputStream)) {
            String s4 = "";
            while (scanner.hasNextLine()) {
                s4 = scanner.nextLine();
                String[] partsOfPrice = s4.split(",");

                prices.put(partsOfPrice[1], Double.valueOf(partsOfPrice[2]));
            } //Записали каждую пару id-name в наш Map prices
            try (FileWriter fileWriter = new FileWriter("result/result.txt")) {
                String maxPriceBook = "";
                double maxPrice = 0;
                String minPriceBook = "";
                double minPrice = 0;


                for (Map.Entry<String, Double> entry : prices.entrySet()) {
                    if (entry.getValue() > maxPrice) {
                        maxPriceBook = entry.getKey();
                        maxPrice = entry.getValue();
                    }
                    if (entry.getValue()==0 ) {
                        minPriceBook = entry.getKey();
                        minPrice = entry.getValue();
                    }
                    if (entry.getValue() < minPrice ) {
                        minPriceBook = entry.getKey();
                        minPrice = entry.getValue();
                    }


                }


                String a = String.format("Самая дорогая книга - \"%s\" (Стоимость - %.2f)", maxPriceBook, maxPrice);
                String b = String.format("Самая дешевая книга - \"%s\" (Стоимость - %.2f)", minPriceBook, minPrice);

                System.out.println(a);
                System.out.println(b);
                fileWriter.write(a);
                fileWriter.write("\n" + b);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}