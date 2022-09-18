#!/usr/bin/env python3

import requests
import json, time
from faker import Faker

ROOT_AGG_PRO = "http://localhost:8787/supplierProcurement"
headers = {
    "Content-Type": "application/json"
}

def getSuppliers():
    response = requests.get(ROOT_AGG_PRO)
    response.raise_for_status()
    data = response.json()
    strData = json.dumps(response.json(), indent=2)
    print(strData)

    for s in data['_embedded']['supplierList']:
        print(s['companyName'])


    


def postSupplier(supplier):
    response = requests.post(f"{ROOT_AGG_PRO}", data=json.dumps(supplier), headers=headers)
    response.raise_for_status()
    data = json.dumps(response.json(), indent=2)
    return response.json()

def putSupplierContact(contact, supplierID):
    response = requests.put(f"{ROOT_AGG_PRO}/{supplierID}/contact", data=json.dumps(contact), headers=headers)
    response.raise_for_status()
    data = json.dumps(response.json(), indent=2)
    return data

getSuppliers()

fake = Faker("en-AU")
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

getSuppliers()