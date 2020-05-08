//CU
module CU(
  input wire [3:0] Ins_op,//Opcoed
  output reg wr_en, m_wr_en, select0, select1,
  output reg [2:0] alu_op
);
 
  always@* begin
    if(Ins_op == 0) begin//Opcoed = 0, add
      wr_en = 1;
      m_wr_en = 0;
      select0 = 0;
      select1 = 0;
      alu_op = 3'b000;
    end
    else if(Ins_op == 1) begin//Opcoed = 1, iadd
      wr_en = 1;
      m_wr_en = 0;
      select0 = 1;
      select1 = 0;
      alu_op = 3'b001;
    end
    else if(Ins_op == 2) begin//Opcoed = 2, st
      wr_en = 0;
      m_wr_en = 1;
      select0 = 0;
      select1 = 0;
      alu_op = 3'b010;
    end
    else if(Ins_op == 3) begin//Opcoed = 3, ld
      wr_en = 1;
      m_wr_en = 0;
      select0 = 0;
      select1 = 1;
      alu_op = 3'b011;
    end
  end

endmodule