# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts.

## Technical Questions

1. What is the difference between == and .equals in java? Provide a code example of each, where they would return different results for an object. Include the code snippet using the hash marks (```) to create a code block.

   In Java, == checks if two references point to the same memory location, while .equals() checks if two objects are logically equal based on their content.
   ```java
   // your code here
   String str1 = new String("hello");
   String str2 = new String("hello");

   System.out.println(str1 == str2); // false (different memory locations)
   System.out.println(str1.equals(str2)); // true (same content)
   ```

2. Logical sorting can be difficult when talking about case. For example, should "apple" come before "Banana" or after? How would you sort a list of strings in a case-insensitive manner?

   To sort a list of strings in a case-insensitive manner in Java, we can use `Comparator.comparing(String::toLowerCase)`, which ensures that sorting ignores case differences.

3. In our version of the solution, we had the following code (snippet)
    ```java
    public static Operations getOperatorFromStr(String str) {
        if (str.contains(">=")) {
            return Operations.GREATER_THAN_EQUALS;
        } else if (str.contains("<=")) {
            return Operations.LESS_THAN_EQUALS;
        } else if (str.contains(">")) {
            return Operations.GREATER_THAN;
        } else if (str.contains("<")) {
            return Operations.LESS_THAN;
        } else if (str.contains("=="))...
    ```
   Why would the order in which we checked matter (if it does matter)? Provide examples either way proving your point.

   The order of checks matters because some operators (>= and <=) contain characters of other operators (> and <).

   For example, if we check for ">" before ">=", then for "4>=4" will return `Operations.GREATER_THAN` which causes to return false eventually, but should return true.


4. What is the difference between a List and a Set in Java? When would you use one over the other?

   A List in Java allows duplicate elements and maintains the order of insertion, making it ideal when order matters or when duplicates are needed.

   A Set does not allow duplicates and does not guarantee a specific order, making it useful when uniqueness is required.

   Therefore, we use List when you need ordered elements or duplicates, and Set when you need uniqueness and do not care about order.


5. In [GamesLoader.java](src/main/java/student/GamesLoader.java), we use a Map to help figure out the columns. What is a map? Why would we use a Map here?

   A Map in Java is a data structure that stores key-value pairs, allowing efficient retrieval of values based on unique keys. Unlike a List or Set, a Map does not store elements in a specific order but provides fast lookups using keys.
   We use here because it provides a way to efficiently associate each GameData column with its corresponding index in the CSV file.


6. [GameData.java](src/main/java/student/GameData.java) is actually an `enum` with special properties we added to help with column name mappings. What is an `enum` in Java? Why would we use it for this application?

   An enum in Java is a special data type used to define a fixed set of constants. It allows you to create a variable that can only take one of the predefined values.
   We use it here because there are predefined attributes like NAME, MIN_PLAYERS, YEAR, etc. so that using an enum ensures we only use valid column names.


7. Rewrite the following as an if else statement inside the empty code block.
    ```java
    switch (ct) {
                case CMD_QUESTION: // same as help
                case CMD_HELP:
                    processHelp();
                    break;
                case INVALID:
                default:
                    CONSOLE.printf("%s%n", ConsoleText.INVALID);
            }
    ``` 

    ```java
    // your code here, don't forget the class name that is dropped in the switch block..
    if (ct == CMD_QUESTION || ct == CMD_HELP) {
         processHelp();
    } else if (ct == INVALID) {
         CONSOLE.printf("%s%n", ConsoleText.INVALID);
    } else {
         CONSOLE.printf("%s%n", ConsoleText.INVALID);
    }
    ```

## Deeper Thinking

ConsoleApp.java uses a .properties file that contains all the strings
that are displayed to the client. This is a common pattern in software development
as it can help localize the application for different languages. You can see this
talked about here on [Java Localization – Formatting Messages](https://www.baeldung.com/java-localization-messages-formatting).

Take time to look through the console.properties file, and change some of the messages to
another language (probably the welcome message is easier). It could even be a made up language and for this - and only this - alright to use a translator. See how the main program changes, but there are still limitations in
the current layout.

Post a copy of the run with the updated languages below this. Use three back ticks (```) to create a code block.

```text
// your consoles output here
*******欢迎来到 BoardGame Arena Planner.*******



 这是一个tool来帮助你们选择board game


 开始之前，请输入你的第一个指令，或者输入?或help来查看指令的格式
```

Now, thinking about localization - we have the question of why does it matter? The obvious
one is more about market share, but there may be other reasons.  I encourage
you to take time researching localization and the importance of having programs
flexible enough to be localized to different languages and cultures. Maybe pull up data on the
various spoken languages around the world? What about areas with internet access - do they match? Just some ideas to get you started. Another question you are welcome to talk about - what are the dangers of trying to localize your program and doing it wrong? Can you find any examples of that? Business marketing classes love to point out an example of a car name in Mexico that meant something very different in Spanish than it did in English - however [Snopes has shown that is a false tale](https://www.snopes.com/fact-check/chevrolet-nova-name-spanish/).  As a developer, what are some things you can do to reduce 'hick ups' when expanding your program to other languages?


As a reminder, deeper thinking questions are meant to require some research and to be answered in a paragraph for with references. The goal is to open up some of the discussion topics in CS, so you are better informed going into industry.

Since there are more than **7,000 languages spoken worldwide**, localization matters significantly in software development as it enables applications to reach a wider audience [^1]. Supporting multiple languages can expand its market reach and accessibility, and also improves user experience by allowing users to interact with software in their native language, making it easier to navigate and understand.

Additionally, poor localization may lead to misunderstandings and even damage a company's reputation. A famous example is when **Parker Pen’s** slogan was mistranslated in Spanish as *"It won’t leak in your pocket and make you pregnant."* instead of *"It won’t leak in your pocket and embarrass you."* [^2]. Such errors highlight the importance of **accurate translation and cultural adaptation** to avoid unintended consequences.

As a developer, we can reduce 'hick ups' by using standardized internationalization (i18n) frameworks, testing translations with **native speakers**, and considering **regional differences** in UI design and formatting.

## References
[^1]: [Ethnologue, 2023](https://www.ethnologue.com/)  
[^2]: [Winsell, 2022](https://winsell-translation.com/blog/parker-s-embarrassment-with-translation-of-advertising)