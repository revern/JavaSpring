DROP TABLE Users;
DROP TABLE Orders;
CREATE TABLE Users (Id INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), FirstName VARCHAR(256), LastName VARCHAR(256), Age INTEGER, PRIMARY KEY (Id));
CREATE TABLE Orders (Id INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), CustomerId INTEGER not NULL, SalersPersonalId INTEGER not NULL, TotalAmount INTEGER not NULL, PRIMARY KEY (Id));