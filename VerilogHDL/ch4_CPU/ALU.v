module ALU(
  input wire [15:0] in0, in1,
  input wire [2:0] alu_op,	//??8???
  output reg [15:0] Z
);

  always@* begin
    case(alu_op)
      3'b000: Z = in0 + in1;  //add
      3'b001: Z = in0 + in1;  //iadd
      3'b010: Z = in0;  //st
      3'b011: Z = in0;  //ld, old Z
    endcase
  end 
  
endmodule
