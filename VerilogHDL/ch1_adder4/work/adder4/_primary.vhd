library verilog;
use verilog.vl_types.all;
entity adder4 is
    port(
        cout            : out    vl_logic;
        sum             : out    vl_logic_vector(3 downto 0);
        ina             : in     vl_logic_vector(3 downto 0);
        inb             : in     vl_logic_vector(3 downto 0);
        cin             : in     vl_logic
    );
end adder4;
