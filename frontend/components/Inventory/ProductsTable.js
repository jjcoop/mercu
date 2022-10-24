import * as React from "react";
import { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";

export default function ProductsTable() {
  const [keyword, setKeyword] = useState("productInventory");
  const [data, setData] = useState([]);
  const fetchData = () => {
    fetch(`http://localhost:8788/${keyword}`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.productList))
      .catch((err) => console.error(err));
  };
  

  useEffect(() => {
    setInterval(() => {
      fetchData();
    }, 1000);
  }, []);

  const columns = [
    { field: "id", headerName: "ID", width: 125, minWidth: 150, maxWidth: 200 },
    { field: "name", headerName: "Product Name", width: 300, minWidth: 150, maxWidth: 300},
    { field: "price", headerName: "Price", width: 125, minWidth: 150, maxWidth: 200},
    { field: "description", headerName: "Description", width: 450, minWidth: 150, maxWidth: 450},
    { field: "quantity", headerName: "Quantity", width: 125, minWidth: 150, maxWidth: 200},
    { field: "parts", headerName: "Parts #", width: 125, minWidth: 150, maxWidth: 200},
  ];

  const rows = [];
  function createData(id, name, price, description, quantity, parts) {
    return {id, name, price, description, quantity, parts};
  }

  data.map((product) =>
      rows.push(
        createData(product.id, product.name, product.price, product.description, product.quantity, product.parts.length)
      )
  );

  return (
    <div style={{ height: 400, width: "100%" }}>
      {/* <Title>Suppliers</Title> */}
      <DataGrid
        rows={rows}
        columns={columns}
        pageSize={5}
        rowsPerPageOptions={[5]}
        // checkboxSelection
      />
    </div>
  );
}