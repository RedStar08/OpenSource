//mux2_1
module mux2_1(
  input wire [15:0] in0, in1,
  input wire select,
  output reg [15:0] out
);
  always@* begin
    case (select)
      0: out = in0;
      1: out = in1;
      default: out = 15'bx;
    endcase
  end
  
endmodule
{//mux2_1
module mux2_1(
  input wire [15:0] in0, in1,
  input wire select,
  output reg [15:0] out
);
  always@* begin
    case (select)
      0: out = in0;
      1: out = in1;
      default: out = 15'bx;
    endcase
  end
  
endmodule

}