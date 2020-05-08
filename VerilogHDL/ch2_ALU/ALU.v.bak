//ALU operate
module ALU(
  input wire [15:0] in0, in1,
  input wire [2:0] select,
  output reg [15:0] out
);

  always@* begin
    
    case(select)
      
      3'b000: out = in0 + in1;
      3'b001: out = in0 - in1;
      3'b010: out = in0 && in1;
      3'b011: out = in0 || in1;
      3'b100: out = in0 << in1;
      3'b101: out = in0 >> in1;
      default: out = 16'bx;
      
    endcase
    
  end
  
endmodule
