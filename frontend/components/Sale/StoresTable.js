import * as React from "react";
import { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";

export default function StoresTable() {
  const [keyword, setKeyword] = useState("sales");
  const [data, setData] = useState([]);
  
  const fetchData = () => {
    fetch(`http://localhost:8789/${keyword}/store`)
      .then((response) => response.json())
      .then((data) => setData(data._embedded.storeList))
      .catch((err) => console.error(err));
  };
  

  useEffect(() => {
    setInterval(() => {
      fetchData();
    }, 1000);
  }, []);

  const columns = [
    { field: "id", headerName: "ID", width: 100, minWidth: 75, maxWidth: 200 },
    { field: "address", headerName: "Address", width: 200, minWidth: 100, maxWidth: 400},
    { field: "managerName", headerName: "Manager Name", width: 125, minWidth: 150, maxWidth: 200},
    { field: "salesCount", headerName: "# Sales", width: 100, minWidth: 75, maxWidth: 200}
  ];

  const rows = [];
  function createData(id, address, managerName, salesCount) {
    return {id, address, managerName, salesCount};
  }

  data.map((store) =>
    rows.push(
      createData(store.id, store.address, store.managerName, store.sales.length)
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