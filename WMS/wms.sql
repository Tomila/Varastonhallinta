-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: mysql.metropolia.fi
-- Generation Time: 31.01.2023 klo 11:08
-- Palvelimen versio: 10.5.18-MariaDB
-- PHP Version: 8.1.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `wms`
--

-- --------------------------------------------------------

--
-- Rakenne taululle `bay`
--

CREATE TABLE `bay` (
  `Bay_ID` int(11) NOT NULL,
  `Rack_ID` int(11) NOT NULL,
  `BayNumber` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `bay`
--

INSERT INTO `bay` (`Bay_ID`, `Rack_ID`, `BayNumber`) VALUES
(1, 1, 1),
(2, 2, 2),
(5, 3, 1);

-- --------------------------------------------------------

--
-- Rakenne taululle `bin`
--

CREATE TABLE `bin` (
  `Bin_ID` int(11) NOT NULL,
  `Rack_ID` int(11) NOT NULL,
  `Bay_ID` int(11) NOT NULL,
  `Level_ID` int(11) NOT NULL,
  `BinNumber` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `bin`
--

INSERT INTO `bin` (`Bin_ID`, `Rack_ID`, `Bay_ID`, `Level_ID`, `BinNumber`) VALUES
(1, 1, 1, 1, 1),
(2, 2, 2, 2, 2),
(31, 3, 5, 5, 1),
(32, 3, 5, 5, 2),
(33, 3, 5, 5, 3);

-- --------------------------------------------------------

--
-- Rakenne taululle `customer`
--

CREATE TABLE `customer` (
  `Customer_ID` int(11) NOT NULL,
  `CustomerName` varchar(50) NOT NULL DEFAULT '',
  `CustomerAddr` varchar(50) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `customer`
--

INSERT INTO `customer` (`Customer_ID`, `CustomerName`, `CustomerAddr`) VALUES
(1, 'Matti Meikäläinen', 'Esimerkkiosoite 2'),
(2, 'John Doe', 'Esimerkkiosoite 3');

-- --------------------------------------------------------

--
-- Rakenne taululle `invoice`
--

CREATE TABLE `invoice` (
  `InvoiceNumber` int(11) NOT NULL,
  `Amount` int(11) NOT NULL,
  `SalesOrderNumber` int(11) DEFAULT NULL,
  `SupplyOrderNumber` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `invoice`
--

INSERT INTO `invoice` (`InvoiceNumber`, `Amount`, `SalesOrderNumber`, `SupplyOrderNumber`) VALUES
(1, 1, 1, 1),
(2, 1, 1, 1);

-- --------------------------------------------------------

--
-- Rakenne taululle `item`
--

CREATE TABLE `item` (
  `Item_ID` int(11) NOT NULL,
  `Supplier_ID` int(11) NOT NULL,
  `ItemName` varchar(50) NOT NULL DEFAULT '',
  `Saldo` int(11) NOT NULL,
  `Price` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `item`
--

INSERT INTO `item` (`Item_ID`, `Supplier_ID`, `ItemName`, `Saldo`, `Price`) VALUES
(1, 1, 'Samsung phone', 20, 450.00),
(2, 3, 'Sony headphones', 40, 150.00),
(3, 1, 'Samsung laptop', 5, 700.00),
(4, 2, 'Lenovo laptop', 30, 550.00);

-- --------------------------------------------------------

--
-- Rakenne taululle `item_location`
--

CREATE TABLE `item_location` (
  `Item_ID` int(11) NOT NULL,
  `Warehouse_ID` int(11) NOT NULL,
  `Section_ID` int(11) NOT NULL,
  `Rack_ID` int(11) NOT NULL,
  `Bay_ID` int(11) NOT NULL,
  `Level_ID` int(11) NOT NULL,
  `Bin_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `item_location`
--

INSERT INTO `item_location` (`Item_ID`, `Warehouse_ID`, `Section_ID`, `Rack_ID`, `Bay_ID`, `Level_ID`, `Bin_ID`) VALUES
(1, 1, 1, 1, 1, 1, 1),
(3, 1, 3, 1, 1, 1, 1),
(4, 1, 3, 1, 1, 1, 1),
(2, 2, 2, 2, 2, 2, 2);

-- --------------------------------------------------------

--
-- Rakenne taululle `language`
--

CREATE TABLE `language` (
  `Language_ID` int(11) NOT NULL,
  `LanguageName` varchar(35) NOT NULL DEFAULT '',
  `active` char(1) NOT NULL DEFAULT 'N',
  `enable` char(1) NOT NULL DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `language`
--

INSERT INTO `language` (`Language_ID`, `LanguageName`, `active`, `enable`) VALUES
(1, 'FI', 'Y', 'Y'),
(2, 'EN', 'N', 'Y'),
(3, 'RU', 'N', 'Y');

-- --------------------------------------------------------

--
-- Rakenne taululle `language_detail`
--

CREATE TABLE `language_detail` (
  `id_language_detail` int(11) NOT NULL,
  `id_language` int(11) NOT NULL DEFAULT 0,
  `id_language_for` int(11) NOT NULL DEFAULT 0,
  `name_language` varchar(35) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `language_detail`
--

INSERT INTO `language_detail` (`id_language_detail`, `id_language`, `id_language_for`, `name_language`) VALUES
(1, 1, 1, 'Suomi'),
(2, 1, 2, 'Finnish'),
(3, 1, 3, 'Финский'),
(4, 2, 1, 'Englanti'),
(5, 2, 2, 'English'),
(6, 2, 3, 'Английский'),
(7, 3, 1, 'Venäjä'),
(8, 3, 2, 'Russian'),
(9, 3, 3, 'Русский');

-- --------------------------------------------------------

--
-- Rakenne taululle `level`
--

CREATE TABLE `level` (
  `Level_ID` int(11) NOT NULL,
  `Rack_ID` int(11) NOT NULL,
  `LevelNumber` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `level`
--

INSERT INTO `level` (`Level_ID`, `Rack_ID`, `LevelNumber`) VALUES
(1, 1, 1),
(2, 2, 2),
(5, 3, 1);

-- --------------------------------------------------------

--
-- Rakenne taululle `ordereditem`
--

CREATE TABLE `ordereditem` (
  `OrderedItem_ID` int(11) NOT NULL,
  `OrderedItemName` varchar(50) NOT NULL DEFAULT '',
  `OrderedItemPrice` varchar(50) NOT NULL DEFAULT '',
  `Quantity` int(11) NOT NULL,
  `SupplyOrderNumber` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `ordereditem`
--

INSERT INTO `ordereditem` (`OrderedItem_ID`, `OrderedItemName`, `OrderedItemPrice`, `Quantity`, `SupplyOrderNumber`) VALUES
(1, 'Smartphone', '200', 1, 1);

-- --------------------------------------------------------

--
-- Rakenne taululle `rack`
--

CREATE TABLE `rack` (
  `Rack_ID` int(11) NOT NULL,
  `Warehouse_ID` int(11) NOT NULL,
  `Section_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `rack`
--

INSERT INTO `rack` (`Rack_ID`, `Warehouse_ID`, `Section_ID`) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 199, 60);

-- --------------------------------------------------------

--
-- Rakenne taululle `sales_order`
--

CREATE TABLE `sales_order` (
  `SalesOrderNumber` int(11) NOT NULL,
  `Customer_ID` int(11) NOT NULL,
  `Done` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `sales_order`
--

INSERT INTO `sales_order` (`SalesOrderNumber`, `Customer_ID`, `Done`) VALUES
(1, 1, b'0'),
(2, 2, b'0');

-- --------------------------------------------------------

--
-- Rakenne taululle `section`
--

CREATE TABLE `section` (
  `Section_ID` int(11) NOT NULL,
  `SectionType` varchar(50) NOT NULL DEFAULT '',
  `Warehouse_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `section`
--

INSERT INTO `section` (`Section_ID`, `SectionType`, `Warehouse_ID`) VALUES
(1, 'Phones ', 1),
(2, 'Headphones', 2),
(3, 'Computers', 1),
(60, 'Test', 199);

-- --------------------------------------------------------

--
-- Rakenne taululle `solditem`
--

CREATE TABLE `solditem` (
  `some_ID` int(11) NOT NULL,
  `SoldItemID` int(11) NOT NULL DEFAULT 0,
  `Quantity` int(11) NOT NULL,
  `SalesOrderNumber` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `solditem`
--

INSERT INTO `solditem` (`some_ID`, `SoldItemID`, `Quantity`, `SalesOrderNumber`) VALUES
(1, 4, 1, 1),
(2, 3, 1, 1),
(3, 2, 1, 2),
(4, 1, 1, 2);

-- --------------------------------------------------------

--
-- Rakenne taululle `supplier`
--

CREATE TABLE `supplier` (
  `Supplier_ID` int(11) NOT NULL,
  `SupplierName` varchar(64) NOT NULL,
  `SupplierAddr` varchar(64) NOT NULL,
  `SupplierDescr` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Vedos taulusta `supplier`
--

INSERT INTO `supplier` (`Supplier_ID`, `SupplierName`, `SupplierAddr`, `SupplierDescr`) VALUES
(1, 'Samsung Electronics Co Ltd', '129 Samseong-ro Yeongtong-gu Gyeonggi-do 16677 Suwon-Shi Korea', 'Samsung phones'),
(2, 'HP', 'Address', 'Descr'),
(3, 'Sony', 'Addr', 'Descr');

-- --------------------------------------------------------

--
-- Rakenne taululle `supply_order`
--

CREATE TABLE `supply_order` (
  `SupplyOrder_ID` int(11) NOT NULL,
  `Supplier_ID` int(11) NOT NULL,
  `SupplyOrderNumber` varchar(24) NOT NULL,
  `OrderAmount` decimal(11,2) NOT NULL DEFAULT 0.00,
  `Date_create` datetime NOT NULL DEFAULT current_timestamp(),
  `Date_modify` datetime NOT NULL DEFAULT current_timestamp(),
  `ContactPerson` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Vedos taulusta `supply_order`
--

INSERT INTO `supply_order` (`SupplyOrder_ID`, `Supplier_ID`, `SupplyOrderNumber`, `OrderAmount`, `Date_create`, `Date_modify`, `ContactPerson`) VALUES
(1, 1, 'AB0000001', '196000.00', '2022-10-26 16:02:17', '2022-12-12 19:40:59', 'Manager'),
(2, 2, 'AB0000002', '22000.00', '2022-10-30 20:22:06', '2022-10-30 20:22:06', 'Manager'),
(3, 3, 'AB13', '1500.00', '2022-12-07 18:21:27', '2022-12-08 21:42:47', 'Manager'),
(4, 1, 'AB14', '0.00', '2022-12-07 18:26:32', '2022-12-07 18:26:32', 'Manager'),
(5, 2, 'AB15', '0.00', '2022-12-07 19:40:18', '2022-12-07 19:40:18', 'Manager'),
(6, 3, 'AB16', '0.00', '2022-12-07 19:50:49', '2022-12-07 19:50:49', 'Mihail'),
(7, 1, 'AB17', '0.00', '2022-12-07 20:18:50', '2022-12-07 20:18:50', 'Mihail'),
(8, 2, 'AB18', '0.00', '2022-12-07 20:41:34', '2022-12-07 20:41:34', 'Mihail'),
(16, 1, 'ab123', '0.00', '2022-12-13 11:26:05', '2022-12-13 11:26:05', 'TEst'),
(17, 2, 'TEST', '0.00', '2022-12-14 17:02:36', '2022-12-14 17:02:36', 'Test');

-- --------------------------------------------------------

--
-- Rakenne taululle `supply_order_item`
--

CREATE TABLE `supply_order_item` (
  `SupplyOrder_ID` int(11) NOT NULL,
  `Item_ID` int(11) NOT NULL,
  `ItemName` varchar(64) NOT NULL,
  `ItemPrice` decimal(10,2) NOT NULL,
  `ItemQuantity` int(11) NOT NULL,
  `Received` int(11) DEFAULT 0,
  `Shelved` int(11) DEFAULT 0,
  `SupplierName` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Vedos taulusta `supply_order_item`
--

INSERT INTO `supply_order_item` (`SupplyOrder_ID`, `Item_ID`, `ItemName`, `ItemPrice`, `ItemQuantity`, `Received`, `Shelved`, `SupplierName`) VALUES
(2, 4, 'Lenovo laptop', '2000.00', 11, 5, 0, 'HP'),
(1, 4, 'Lenovo laptop', '2000.00', 50, 45, 45, 'HP'),
(2, 4, 'Lenovo laptop', '550.00', 10, 0, 0, 'HP'),
(1, 3, 'Samsung laptop', '700.00', 10, 6, 5, 'Samsung Electronics Co Ltd'),
(3, 2, 'Sony headphones', '150.00', 10, 0, 0, 'Sony'),
(1, 3, 'Samsung laptop', '700.00', 10, 6, 5, 'Samsung Electronics Co Ltd'),
(1, 3, 'Samsung laptop', '700.00', 10, 6, 5, 'Samsung Electronics Co Ltd'),
(1, 3, 'Samsung laptop', '700.00', 10, 6, 5, 'Samsung Electronics Co Ltd'),
(1, 3, 'Samsung laptop', '700.00', 10, 6, 5, 'Samsung Electronics Co Ltd'),
(1, 3, 'Samsung laptop', '700.00', 10, 6, 5, 'Samsung Electronics Co Ltd'),
(1, 3, 'Samsung laptop', '700.00', 10, 6, 5, 'Samsung Electronics Co Ltd'),
(1, 1, 'Samsung phone', '450.00', 10, 0, 0, 'Samsung Electronics Co Ltd');

-- --------------------------------------------------------

--
-- Rakenne taululle `warehouse`
--

CREATE TABLE `warehouse` (
  `Warehouse_ID` int(11) NOT NULL,
  `WarehouseName` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Vedos taulusta `warehouse`
--

INSERT INTO `warehouse` (`Warehouse_ID`, `WarehouseName`) VALUES
(1, 'Main'),
(2, 'Smaller'),
(57, 'Electronics'),
(199, 'Test');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bay`
--
ALTER TABLE `bay`
  ADD PRIMARY KEY (`Bay_ID`),
  ADD KEY `bay_ibfk_1` (`Rack_ID`);

--
-- Indexes for table `bin`
--
ALTER TABLE `bin`
  ADD PRIMARY KEY (`Bin_ID`),
  ADD KEY `bin_ibfk_1` (`Rack_ID`),
  ADD KEY `bin_ibfk_2` (`Bay_ID`),
  ADD KEY `bin_ibfk_3` (`Level_ID`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`Customer_ID`);

--
-- Indexes for table `invoice`
--
ALTER TABLE `invoice`
  ADD PRIMARY KEY (`InvoiceNumber`),
  ADD KEY `SalesOrderNumber` (`SalesOrderNumber`),
  ADD KEY `SupplyOrderNumber` (`SupplyOrderNumber`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`Item_ID`),
  ADD KEY `Supplier_ID` (`Supplier_ID`) USING BTREE;

--
-- Indexes for table `item_location`
--
ALTER TABLE `item_location`
  ADD KEY `Warehouse_ID` (`Warehouse_ID`),
  ADD KEY `Section_ID` (`Section_ID`),
  ADD KEY `Item_ID` (`Item_ID`),
  ADD KEY `Level_ID` (`Level_ID`) USING BTREE,
  ADD KEY `item_location_ibfk_4` (`Rack_ID`),
  ADD KEY `item_location_ibfk_5` (`Bay_ID`),
  ADD KEY `item_location_ibfk_7` (`Bin_ID`);

--
-- Indexes for table `language`
--
ALTER TABLE `language`
  ADD PRIMARY KEY (`Language_ID`),
  ADD KEY `id_language` (`Language_ID`);

--
-- Indexes for table `language_detail`
--
ALTER TABLE `language_detail`
  ADD PRIMARY KEY (`id_language_detail`),
  ADD KEY `id_language_detail` (`id_language_detail`),
  ADD KEY `id_language` (`id_language`);

--
-- Indexes for table `level`
--
ALTER TABLE `level`
  ADD PRIMARY KEY (`Level_ID`),
  ADD KEY `level_ibfk_1` (`Rack_ID`);

--
-- Indexes for table `ordereditem`
--
ALTER TABLE `ordereditem`
  ADD PRIMARY KEY (`OrderedItem_ID`),
  ADD KEY `SupplyOrderNumber` (`SupplyOrderNumber`);

--
-- Indexes for table `rack`
--
ALTER TABLE `rack`
  ADD PRIMARY KEY (`Rack_ID`),
  ADD KEY `rack_ibfk_1` (`Warehouse_ID`),
  ADD KEY `rack_ibfk_2` (`Section_ID`);

--
-- Indexes for table `sales_order`
--
ALTER TABLE `sales_order`
  ADD PRIMARY KEY (`SalesOrderNumber`),
  ADD KEY `Customer_ID` (`Customer_ID`);

--
-- Indexes for table `section`
--
ALTER TABLE `section`
  ADD PRIMARY KEY (`Section_ID`),
  ADD KEY `Warehouse_ID` (`Warehouse_ID`);

--
-- Indexes for table `solditem`
--
ALTER TABLE `solditem`
  ADD PRIMARY KEY (`some_ID`) USING BTREE,
  ADD KEY `SalesOrderNumber` (`SalesOrderNumber`),
  ADD KEY `SoldItemID` (`SoldItemID`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`Supplier_ID`);

--
-- Indexes for table `supply_order`
--
ALTER TABLE `supply_order`
  ADD PRIMARY KEY (`SupplyOrder_ID`),
  ADD KEY `Supplier_ID` (`Supplier_ID`) USING BTREE;

--
-- Indexes for table `supply_order_item`
--
ALTER TABLE `supply_order_item`
  ADD KEY `supply_order_item_ibfk_1` (`SupplyOrder_ID`),
  ADD KEY `Item_ID` (`Item_ID`) USING BTREE;

--
-- Indexes for table `warehouse`
--
ALTER TABLE `warehouse`
  ADD PRIMARY KEY (`Warehouse_ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bay`
--
ALTER TABLE `bay`
  MODIFY `Bay_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `bin`
--
ALTER TABLE `bin`
  MODIFY `Bin_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `Customer_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `Item_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `language`
--
ALTER TABLE `language`
  MODIFY `Language_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `language_detail`
--
ALTER TABLE `language_detail`
  MODIFY `id_language_detail` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `level`
--
ALTER TABLE `level`
  MODIFY `Level_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `ordereditem`
--
ALTER TABLE `ordereditem`
  MODIFY `OrderedItem_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `rack`
--
ALTER TABLE `rack`
  MODIFY `Rack_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `section`
--
ALTER TABLE `section`
  MODIFY `Section_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=118;

--
-- AUTO_INCREMENT for table `solditem`
--
ALTER TABLE `solditem`
  MODIFY `some_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `Supplier_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `supply_order`
--
ALTER TABLE `supply_order`
  MODIFY `SupplyOrder_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `warehouse`
--
ALTER TABLE `warehouse`
  MODIFY `Warehouse_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=314;

--
-- Rajoitteet vedostauluille
--

--
-- Rajoitteet taululle `bay`
--
ALTER TABLE `bay`
  ADD CONSTRAINT `bay_ibfk_1` FOREIGN KEY (`Rack_ID`) REFERENCES `rack` (`Rack_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Rajoitteet taululle `bin`
--
ALTER TABLE `bin`
  ADD CONSTRAINT `bin_ibfk_1` FOREIGN KEY (`Rack_ID`) REFERENCES `rack` (`Rack_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `bin_ibfk_2` FOREIGN KEY (`Bay_ID`) REFERENCES `bay` (`Bay_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `bin_ibfk_3` FOREIGN KEY (`Level_ID`) REFERENCES `level` (`Level_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Rajoitteet taululle `level`
--
ALTER TABLE `level`
  ADD CONSTRAINT `level_ibfk_1` FOREIGN KEY (`Rack_ID`) REFERENCES `rack` (`Rack_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Rajoitteet taululle `rack`
--
ALTER TABLE `rack`
  ADD CONSTRAINT `rack_ibfk_1` FOREIGN KEY (`Warehouse_ID`) REFERENCES `warehouse` (`Warehouse_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `rack_ibfk_2` FOREIGN KEY (`Section_ID`) REFERENCES `section` (`Section_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Rajoitteet taululle `sales_order`
--
ALTER TABLE `sales_order`
  ADD CONSTRAINT `Customer_ID` FOREIGN KEY (`Customer_ID`) REFERENCES `customer` (`Customer_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Rajoitteet taululle `section`
--
ALTER TABLE `section`
  ADD CONSTRAINT `section_ibfk_1` FOREIGN KEY (`Warehouse_ID`) REFERENCES `warehouse` (`Warehouse_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Rajoitteet taululle `solditem`
--
ALTER TABLE `solditem`
  ADD CONSTRAINT `SalesOrderNumber` FOREIGN KEY (`SalesOrderNumber`) REFERENCES `sales_order` (`SalesOrderNumber`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `SoldItemID` FOREIGN KEY (`SoldItemID`) REFERENCES `item` (`Item_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Rajoitteet taululle `supply_order_item`
--
ALTER TABLE `supply_order_item`
  ADD CONSTRAINT `supply_order_item_ibfk_1` FOREIGN KEY (`SupplyOrder_ID`) REFERENCES `supply_order` (`SupplyOrder_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `supply_order_item_ibfk_2` FOREIGN KEY (`Item_ID`) REFERENCES `item` (`Item_ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
