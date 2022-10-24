#!/usr/bin/env python3

import requests
import json
import time
from faker import Faker
from random import choice


class Microservice:
    def __init__(self, service, port) -> None:
        self.service = service
        self.port = port
        self.url = self.createUrl()

    def createUrl(self):
        return f"http://localhost:{self.port}/{self.service}"


fake = Faker("en-AU")
procurems = Microservice(service="supplierProcurement", port=8787)
inventoryms = Microservice(service="productInventory", port=8788)
salems = Microservice(service="sales", port=8789)
bi = Microservice(service="bi-sales", port=8790)

HEADER = {
    "Content-Type": "application/json"
}

BIKE_PARTS = ["Saddle", "Seatpost", "Rim", "Tyre", "Chain ring", "Front Spacer", "Crank", "Rear Spacer", "Pedal",
              "Large Rear Sprocket", "Small Rear Sprocket", "Whell hub", "Air Chamber", "Chain", "Center Spacer", "Bottom Bracket", "Spoke"]


def getSuppliers():
    response = requests.get(procurems.url)
    response.raise_for_status()
    data = response.json()
    # strData = json.dumps(response.json(), indent=2)
    # print(strData)

    supplierName = []
    for s in data['_embedded']['supplierList']:
        supplierName.append(s['companyName'])

    return supplierName


def getProducts():
    response = requests.get(inventoryms.url)
    response.raise_for_status()
    data = response.json()
    # strData = json.dumps(response.json(), indent=2)
    # print(strData)

    productName = []
    for s in data['_embedded']['productList']:
        productName.append(s['name'])

    return productName


def getUnavailableProducts():
    response = requests.get(f"{salems.url}/unavailable")
    response.raise_for_status()
    data = response.json()
    return data


def postSupplier(supplier):
    response = requests.post(
        f"{procurems.url}", data=json.dumps(supplier), headers=HEADER)
    response.raise_for_status()
    # data = json.dumps(response.json(), indent=2)
    return response.json()


def postProduct(product):
    response = requests.post(f"{inventoryms.url}",
                             data=json.dumps(product), headers=HEADER)
    response.raise_for_status()
    # data = json.dumps(response.json(), indent=2)
    return response.json()


def postStore(store):
    response = requests.post(f"{salems.url}/store",
                             data=json.dumps(store), headers=HEADER)
    response.raise_for_status()
    # data = json.dumps(response.json(), indent=2)
    return response.json()


def putSupplierContact(contact, supplierID):
    response = requests.put(
        f"{procurems.url}/{supplierID}/contact", data=json.dumps(contact), headers=HEADER)
    response.raise_for_status()
    # data = json.dumps(response.json(), indent=2)
    # return data


def putProductPart(part, productID):
    response = requests.put(
        f"{inventoryms.url}/{productID}/part", data=json.dumps(part), headers=HEADER)
    response.raise_for_status()
    # data = json.dumps(response.json(), indent=2)
    # return data


def postStoreSale(sale, storeID):
    response = requests.post(
        f"{salems.url}/store/{storeID}", data=json.dumps(sale), headers=HEADER)
    response.raise_for_status()
    # data = json.dumps(response.json(), indent=2)
    # return data


def postOnlineSale(sale):
    response = requests.post(
        f"{salems.url}/online", data=json.dumps(sale), headers=HEADER)
    response.raise_for_status()
    # data = json.dumps(response.json(), indent=2)
    # return data


def backorder(unavailableSaleID):
    response = requests.get(f"{salems.url}/backorder/{unavailableSaleID}")
    response.raise_for_status()
    data = response.json()
    return data


if input("Create Suppliers and contacts: Y/N ").upper() == "Y":
    for x in range(20):
        supplier = {"companyName": fake.company(
        ), "base": fake.administrative_unit()}
        print(supplier)
        sup = postSupplier(supplier)
        for i in range(10):
            ln = fake.last_name()
            contact = {
                "fname": fake.first_name(),
                "lname": ln,
                "phone": fake.phone_number(),
                "email": f"{ln}{fake.random_number(digits=3, fix_len=True)}@example.com",
                "position": fake.job()
            }
            print(contact)
            putSupplierContact(contact, int(sup['id']))

if input("Create Products and Parts: Y/N ").upper() == "Y":
    check = True
    counter = 777677
    productNumber = 20
    partNumber = 7
    if len(getSuppliers()) < 1:
        print("Error: Suppliers are needed")
        check = False
    if check:
        supplierNames = getSuppliers()
        for x in range(productNumber):
            product = {
                "name": f"{fake.color_name()} {fake.word()}",
                "price": float(f"{fake.random_number(digits=2, fix_len=True)}.{fake.random_number(digits=1, fix_len=True)}"),
                "description": fake.sentence(nb_words=7, variable_nb_words=False),
                "quantity": fake.random_number(digits=2, fix_len=True)
            }
            print(product)
            resProduct = postProduct(product)
            for PART in BIKE_PARTS:
                manufacturer = choice(supplierNames)
                partName1 = f"{fake.word()}".upper()
                partName2 = f"{fake.word()}".upper()
                partName3 = f"{fake.word()}".upper()
                partName4 = f"{fake.word()}".upper()
                part = {
                    "partName": PART,
                    "partDescription": fake.sentence(nb_words=7, variable_nb_words=False),
                    "manufacturer": manufacturer,
                    "quantity": fake.random_number(digits=3, fix_len=True)
                }
                print(part)
                putProductPart(part, int(resProduct['id']))

productNames = getProducts()

if input("Create Stores and Store sales: Y/N ").upper() == "Y":
    storeNumber = 5
    for x in range(storeNumber):
        store = {
            "address": fake.address(),
            "managerName": fake.first_name()
        }
        storeID = postStore(store)
        for inSale in range(5):
            inStoreSale = {
                "productName": choice(productNames),
                "quantity": fake.random_number(digits=1, fix_len=True)
            }
            print(inStoreSale)
            postStoreSale(inStoreSale, storeID['id'])

if input("Create Online Sales: Y/N ").upper() == "Y":
    saleNumber = 25
    for x in range(saleNumber):
        ln = fake.last_name()
        sale = {
            "customerName": f"{fake.first_name()} {ln}",
            "address": fake.address(),
            "productName": choice(productNames),
            "quantity": fake.random_number(digits=1, fix_len=True)
        }
        print(sale)
        postOnlineSale(sale)


if input("Create Backorders: Y/N ").upper() == "Y":
    unavailableProducts = getUnavailableProducts()
    for u in unavailableProducts:
        print(backorder(u['id']))

if input("Create High Traffic Online Sales: Y/N ").upper() == "Y":
    while (True):
        ln = fake.last_name()
        sale = {
            "customerName": f"{fake.first_name()} {ln}",
            "address": fake.address(),
            "productName": choice(productNames),
            "quantity": 2
        }
        print(sale)
        postOnlineSale(sale)
        print("another request ...")
        time.sleep(2)
