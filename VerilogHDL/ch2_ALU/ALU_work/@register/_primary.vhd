library verilog;
use verilog.vl_types.all;
entity \Register\ is
    port(
        WE              : in     vl_logic;
        clk             : in     vl_logic;
        R_port0         : in     vl_logic_vector(2 downto 0);
        R_port1         : in     vl_logic_vector(2 downto 0);
        W_port          : in     vl_logic_vector(2 downto 0);
        Write           : in     vl_logic_vector(15 downto 0);
        Read0           : out    vl_logic_vector(15 downto 0);
        Read1           : out    vl_logic_vector(15 downto 0)
    );
end \Register\;
