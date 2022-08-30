import Head from "next/head";
import Image from "next/image";
import { useEffect, useState } from "react";

export default function procurement() {
  const [keyword, setKeyword] = useState("supplierProcurement");
  const [data, setData] = useState([]);
  const fetchData = () => {
    fetch(`http://localhost:8787/${keyword}`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.supplierList))
      .catch((err) => console.error(err));

  };

  useEffect(() => {
    fetchData();
  }, []);

  return (

        <div>
          {data.map((supplier, index) => (
            <a key={index} href={supplier['_links']['self']['href']}>
              <h2>{supplier['companyName']} &rarr;</h2>
              <p>Based in {supplier["base"]} with {supplier['contacts'].length} contacts.</p>
              {supplier['contacts'].map((c, i) => (
                <div>
                <a href={`http://localhost:8080/contacts/${c['id']}`}>
                  <p>id: {c['id']}</p>
                  <p>{c['position']} &rarr;</p>
                </a>
                </div>
              ))}

            </a>
          ))}
        </div>
  );
}
