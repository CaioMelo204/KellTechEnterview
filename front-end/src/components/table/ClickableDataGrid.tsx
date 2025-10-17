import { DataGrid, type GridRowParams } from "@mui/x-data-grid";
import styles from "./ClickableDataGrid.module.css";
import React from "react";
import type { PartnerResumeInterface } from "../../interfaces/partner-resume.interface.ts";

interface ClickableDataGridProps {
  data: PartnerResumeInterface[];
  onSelectedCnpj?: (cnpj: string) => void;
}

export const ClickableDataGrid: React.FC<ClickableDataGridProps> = ({
  data,
  onSelectedCnpj,
}: ClickableDataGridProps) => {
  const rows = data.map((item: PartnerResumeInterface) => {
    return {
      id: item.id,
      nome: item.nome,
      cnpj: item.cnpj,
      participacao: item.participacao,
    };
  });

  const columns = [
    { field: "nome", headerName: "Nome", flex: 1 },
    { field: "cnpj", headerName: "CNPJ", width: 300 },
    { field: "participacao", headerName: "Participação", flex: 1 },
  ];

  return (
    <div className={styles.gridContainer}>
      <DataGrid
        rows={rows}
        columns={columns}
        onRowClick={(params: GridRowParams) => {
          if (onSelectedCnpj) onSelectedCnpj(params.row.id);
        }}
        sx={{
          cursor: "pointer",
        }}
        className={styles.dataGrid}
      />
    </div>
  );
};
