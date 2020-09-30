# Assignment 1 - Group Expenses

# Task description:

## Scenario

You, (e.g. Alice), go with a group of friends (e.g. Bob, Charlie and Eva) for a trip. During the trip, each of you pay for some common expenses. For example, buying everyone a cinema tickets and popcorn (you, Alice, 50), buying buss tickets (Bob, 20), train tickets (Charlie, 20) and paying for lunch for everyone (Eve, 30). At the end of the trip, you want to make sure that everyone contributed evenly for the trip. You make a balance sheet:

   * Alice - 50
   * Bob - 20
   * Charlie - 20
   * Eve - 30
   * Total (the sum of all expenses): 120
   * Even split of costs: 30

We see, that everyone should have contributed 30, such that all shared expenses are evenly distributed among everyone in the trip group. So, to make it even, we need:

   * Eve pays none - she has contributed exactly what was needed.
   * Bob pays 10 to Alice (Alice contributed more than average, and Bob less)
   * Charlie pays 10 to Alice

After that, everything is even!  And you can go for another trip.


## Description

For assignment 1, we will build a simple app, that allows a group of friends to calculate, who should pay whom after the trip.  The app will have entry mode, where names and amounts of who payed what can be entered. For simplicity, each name should only be allowed to be entered once. So, if Alice pays for cinema (50), and for buss (20), the app only allows a single row, that states: Alice - 70. In other words, the aggregation of expenses by the participant is beyond the scope of this assignment.

Once all the expenses are entered, the app will calculate who should pay how much to whom. The app should work for arbitrary number of participants. 

The app is currency independent, and should work with 2 decimal places. Internally, all amounts should be represented as INTEGERS, but, we want the amounts in the User Interface to be presented as FLOATS with 2 decimal places. 

The specification provides all the details of what is required. It also leaves some aspects out to allow creative space for interpretation. We may need to update the specification based on this interplay between what is required and what is left for creativity. 


## Specification

There are additional comments/specifications in the project itself (as TODO items or comments). Use them. If there is inconsistency in specifications, report by submitting an issue.

### Project structure

The project consists of a single pre-generated Activity (MainActivity) and three UI elements, which we will refer to as Views. How the Views are implemented is NOT specified on purpose - one can implement them using Activites and simple ViewCompontents in XML, or, using Fragments. What is important, is that UI elements (Views) have certain structure, certain UI elements and they have specific IDs, that the tests can use.

### Locale

   * The decimal place separator, can be either dot or comma. When the data is presented to the user, the appropriate formatting should be used (for Norwegian it should be comma, for English it should be dot). However, when the user enters the number, the user should be able to use EITHER of the two separators, exchangeably. 



### Notes

   * Instead of the default icon, the app should have a custom-designed Icon, made by yourself. 
   * Any txt_field (TextView) that contains dynamically generated text (eg. total for expenses in the MainActivity view) should only contain the automatically generated text. All static text, such as "Total amount: " should be kept in a separate TextView (separating `labels` of UI elements from `values`.
   * In general, any labels that describe the UI to the user, are not tested. They can have arbitrary `id`, or, one does not need to have `id` associated with them. 



### UI in MainActivity View

| id | description |
| ------ | ------ |
| `main_view` | main, top-level layout `id`|
| `txt_expenses_total` | TextView, that contains the sum of all expenses so far. |
| `txt_expenses_avr` | TextView, that contains the arithmetic average of all expenses so far. |
| | Note: the labels for the above two fields should be included in the UI separately. The IDs for the labels are not used in tests, and can be arbitrary. |
| `tbl_expenses` | TableView, that contains rows per each personal expense. The raw should contain: the name of a person, the amount, and the info text associated with the expense. |
| `btn_add_data`| button, with text `Add`. When pressed, DataEntry view should be shown |
| `btn_settlement`| button, with text `Settlement`. When pressed, Settlement view should be shown. This button is disabled until at least 2 entries are entered into the app.|
| back button | Left unhandled. Default behaviour. |


### UI in DataEntry View

| id | description |
| ------ | ------ |
| `dataentry_view` | top-level layout `id` |
| `edit_person` | EditText, for name entry of a person. |
| `edit_amount` | EditText, for the expenses amount for a given person. |
| `edit_description` | EditText for expense description. |
| `btn_cancel` | button, with text `Cancel`. When pressed, DataEntry view disappears and MainActivity view appears |
| `btn_add_expense` | button, with text `Add`. When pressed, it should add the SingleExpense instance to the list of Expenses. The button should only be active if all the 3 data entry fields have proper data.
| `txt_add_expenses_error` | textview, with a text explaining problems with the data entry fields. This text should only be shown when the `btn_add_expense` is inactive due to data validation failing.|
| back button | App goes back to the MainActivity view |


### UI in Settlement View

| id | description |
| ------ | ------ |
| `settlement_view` | main layout `id` for the Settlement view |
| `tbl_settlements` | TableView, with each row representing a settlement transaction between two people. The columns are: the name of the payer (from), the name of the payee (to), the amount |
| back button | App goes back to the MainActivity view |



### Data entry fields

All user-entered data needs to be sanitised. 

| value | description |
| ---- | ---- |
| names | The names need to follow these rules: (1) the first letter of each name token is always capitalised; (2) the trailing/leading space around tokens is removed (trimmed); (3) the tokens are separated by single space, or joined by `-`; (4) only letters are allowed in the names (no special characters, no punctuation with the exception of `-` and no numbers); (5) only single-token or double-token names are accepted |
| amounts | the amounts entered by the user should be integers or floats with at most 2 decimal places |


### Persistence 

   * The app MUST handle screen rotation properly. The app should work both, in Landscape and Portrait modes. No data should be lost when changing the phone orientation. No exceptions should be thrown when changing the phone orientation. 
   * The app does not need to store the data persistently. That means, if a user shuts down the app, then the data might be reset to zero. If you want, you could store the data in a persistent storage (preferences, file system, or DB). This is optional.

## Code and professionalism

   * Fork the project template for Assignment 1 and use it as a basis for your implementation.
   * Use proper Kotlin coding conventions.
   * Keep public classes in individual files with the matching class to file names.
   * Where already provided, use the existing classes and methods/functions. Fill in the body where needed.
   * Write your own private/public utility classes as you like, to improve the quality of your code.
   * Your submitted solution should not have any Linter errors, and should pass all the provided JUnit and Espresso tests.
   
### Extra credits

   * Students who contribute tests through merge requests will obtain Extra Credits for the assignment. Note - when submitting merge requests make sure your code contains ONLY tests/template code, and does not provide any part of the solution to the problems of the assignment. Best strategy would be to keep your test contribution in your own separate branch and use that for merge requests. 


### Domain modelling

Because it is a simple school exercise, focusing more on Android rather than properly programming the domain, we have made some simplifications. 

   * SingleExpense represents the single expense for a given Person.
   * Expenses represents the collection of all the expenses by all the People.
   * Transaction represents a single settlement transaction between the payer, payee and the amount. Note, there is no timestamps, and no time associated with a single expense or with transactions.
   * The type `Amount` should normally be defined explicitly, for this domain and used to represent monetary amounts. For simplicity, we use `Amount` as `Long`. This is not to be used in production as it may lead to confusing ordinary Long numbers with financial amounts.
   * The type `Person` should normally be defined for this domain, to represent the owner of an account or a payer/payee in transactions. For simplicity, we use the type `String` to represent a person (by name). This is not to be used in production. Same as with the case of Amount/Long, using strings instead of proper type can lead to a substitution of ordinary string in places where Person is expected. This can lead to hard-to-find bugs once the complexity of the project grows.
