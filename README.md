rfhsdisudfh# HYL-Hackathon

Your application must do the following:
1. Take in user input for 1) a furniture category, 2) its type, and 3) the number of items requested.
2. Calculate and output the cheapest option for creating the requested pieces of furniture or
specify if the request is not possible to fill.
- Furniture items must be purchased as a whole. Single components cannot be purchased separately. A future expansion phase will add a buy-back option for individual unused components, so your code should be well-documented for future developers to use.
- Each furniture item can only be built from items of the same type (e.g. mesh chair, desk lamp, etc.). Components are not interchangeable across types (including light bulbs).
- Even used furniture costs money! The price of an item will depend on its number of usable components and their condition.
- If there are multiple combinations for the cheapest price, select any one combination. - You are welcome to add furniture data to the database for testing purposes.
3. If the request can be filled, produce a formatted order form in a .txt format. An example order form file is provided.
4. When an order form is produced, the database should also be updated to specify that the selected items are no longer available in inventory.
5. If a request cannot be filled, the names of suggested manufacturers should be included in the output message. All possible manufacturers are included in each furniture table. Note that some of the manufacturers do not sell all furniture items.
