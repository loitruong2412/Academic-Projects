# GroupProject_LocalHosts_Spendwise

## Team members:
1. Zack Manfredi
2. Mac Marks
3. Andres Schuchert
4. Loi Truong

# App Description

Spendwise is a budgeting web application which allows users to add and track their monthly incomes and expenses, as well as provides them with visualizations in the form of bar charts and pie charts of their monthly and annual cash flow. These charts will help users budget their spending for the future.

This full-stack web application has been developed using the MERN stack (i.e. MongoDB, Express, React and Node). It is composed of two applications: <a href="https://github.ccs.neu.edu/loitruong2412/GroupProject_LocalHosts_UI">UI</a> and <a href="https://github.ccs.neu.edu/loitruong2412/GroupProject_LocalHosts_API">API</a>.

# Deployment

API: https://spendwise-api.herokuapp.com
UI: https://spendwise-ui.herokuapp.com

# How to run locally

In the ui directory, run `npm start`

In the api directory, run `npm start`


# Iteration 3

## Progress

1. Implemented float input for income/expense Amount

2. Converted Date and Year input into a calendar picker using `react-datepicker`

3. Styling:
- Converted bootstrap to react-bootstrap for Add and Edit components.
- Navbar styling and coloring.
- Changed pie chart into doughtnut chart.
- Validation state for FormGroup. The input bar will turn red if the input is invalid and green if valid.

4. Fixed eslint errors.

5. Integrated the Filter component to filter out monthly income/expense based on year.

6. Added About and Page Not Found components.

7. Removed redundant code. Combined income and expense collections into one collection called **items**.

## Contributions

1. Zack Manfredi: chart styling, fixing eslint error, debugging
2. Mac Marks: chart styling, debugging, monthly income/expense chart based on filter
3. Andres Schuchert: date picker, debugging, float input, converted bootstrap to react-boostrap
4. Loi Truong: debugging, styling, float input, about & page not found components, removed redundant code


# Iteration 2

## Progress

1. New table format using `react-bootstrap-table2`:
-  Search bar: this acts as a secondary filter and can be used to search for anything value in any field.
- Sorting functionality: users can sort the data by title, type, month, year, amount. Multi-column sorting is not supported.
- Pagination: the table data is automatically grouped into pages of 10 items. The number of items per page can be chosen using the drop down list.

2. Form validation and confirmation using toasts:
- Added success confirmation when users successfully add/update an income/expense item.
- Added error message when users try to add/update an income/expense item whose title is less than 3 characters and/or amount is greater than 1 billion.

3. Charts for report component using `chart.js`:
- Pie chart that shows the total amount of income versus expense so users know the ratio.
- Monthly and yearly incomes, monthly and yearly expenses as horizontal bar charts.
- Incomes and expenses by type/category as vertical bar charts.

## Contributions

1. Zack Manfredi: Reports and charts (pie chart, horizontal and vertical bar charts)
2. Mac Marks: Reports and charts (pie chart, horizontal and vertical bar charts)
3. Andres Schuchert: Form validation, toast messages
4. Loi Truong: New table format, search bar, sorting, pagination

# Iteration 1

## Progress

1. Instead of using the code base from the book, we started from scratch using the tutorials from freeCodeCamp.org (see References).

2. We used Mongoose schema as a way of connecting to MongoDB, instead of GraphQL as in the book. To test the basic CRUD operations on the api side, we used Insomnia (similar to Postman).

3. For the front-end, we have have React components created to represent the CRUD functionality.

4. Routing, links and the back end is connected to the front end.

5. We used some code from the book for other functionalities, specifically the Filter component. Styling was done using React Bootstrap similarly to the book's project.

6. We also created some scripts to populate the income and expense list with mock data.

7. Reports/charts are in progress and not included in this Iteration.

## Contributions

1. Zack Manfredi: Reports and charts (in progress)
2. Mac Marks: Reports and charts (in progress)
3. Andres Schuchert: Filter component, db scripts, form validation
4. Loi Truong: Code base, basic CRUD functionality, filter component

# Screenshots & Descriptions

Refer to these repos:

API: https://github.ccs.neu.edu/loitruong2412/GroupProject_LocalHosts_API

UI: https://github.ccs.neu.edu/loitruong2412/GroupProject_LocalHosts_UI
