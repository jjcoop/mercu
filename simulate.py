#!/usr/bin/env python3

import requests
import json, time
from faker import Faker

class Microservice:
    def __init__(self, service, port) -> None:
        self.service = service
        self.port = port
        self.url = self.createUrl()
        
    def createUrl(self):
        return f"http://localhost:{self.port}/{self.service}"
    
fake = Faker("en-AU")
procurems = Microservice(service="supplierProcurement",port=8787)
inventoryms = Microservice(service="productInventory",port=8788)
salems = Microservice(service="sales",port=8789)
bi = Microservice(service="bi-sales",port=8790)

HEADER = {
    "Content-Type": "application/json"
}


def getSuppliers():
    response = requests.get(procurems.url)
    response.raise_for_status()
    data = response.json()
    # strData = json.dumps(response.json(), indent=2)
    # print(strData)

    names = []
    for s in data['_embedded']['supplierList']:
        names.append(s['companyName'])
        
    return names

def postSupplier(supplier):
    response = requests.post(f"{procurems.url}", data=json.dumps(supplier), headers=HEADER)
    response.raise_for_status()
    data = json.dumps(response.json(), indent=2)
    return response.json()

def putSupplierContact(contact, supplierID):
    response = requests.put(f"{procurems.url}/{supplierID}/contact", data=json.dumps(contact), headers=HEADER)
    response.raise_for_status()
    data = json.dumps(response.json(), indent=2)
    return data

if input("Create Suppliers and contacts: Y/N ") == "Y":
    for x in range(20):
        supplier = {"companyName": fake.company(), "base": fake.administrative_unit()}
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
            putSupplierContact(contact, int(sup['id']))

if input("Create Products and Parts: Y/N ") == "Y":
    check = True
    if len(getSuppliers()) < 1:
        print("Error: Suppliers are needed")
        check = False
    if check:
        supplierNames = getSuppliers()
        for name in supplierNames:
            product = {
                "name": fake.company(),
                "price": f"{fake.random_number(digits=4, fix_len=True)}.{fake.random_number(digits=2, fix_len=True)}",
                "description": f"{fake.color_name()}",
                "quantity": fake.random_number(digits=1, fix_len=True)
                }

