import * as React from "react";
import { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import Title from "./Title";

//GET LINK: http://localhost:8788/productInventory/parts
export default function products() {
  const [keyword, setKeyword] = useState("productInventory");
  const [data, setData] = useState([]);
  const fetchData = () => {
    fetch(`http://localhost:8788/${keyword}/parts`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.partList))
      .catch((err) => console.error(err));
  };
  

  useEffect(() => {
    fetchData();
  }, []);

  const columns = [
    { field: "id", headerName: "ID", width: 125, minWidth: 150, maxWidth: 200 },
    { field: "partName", headerName: "Product Name", width: 125, minWidth: 150, maxWidth: 200},
    { field: "partDescription", headerName: "Part Description", width: 125, minWidth: 150, maxWidth: 200},
    { field: "manufacturer", headerName: "manufacturer", width: 125, minWidth: 150, maxWidth: 200},
    { field: "quantity", headerName: "Quantity", width: 125, minWidth: 150, maxWidth: 200},
  ];

  const rows = [];
  function createData(id, partName, partDescription, manufacturer, quantity) {
    return {id, partName, partDescription, manufacturer, quantity};
  }

  data.map((product) =>
      rows.push(
        createData(product.id, product.partName, product.partDescription, product.manufacturer, product.quantity)
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