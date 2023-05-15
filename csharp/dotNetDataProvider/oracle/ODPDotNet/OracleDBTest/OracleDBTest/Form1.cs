using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Oracle.ManagedDataAccess.Client;

namespace OracleDBTest
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            string serverAddress = "localhost";
            string serviceName = "ORCL";
            string userId = "scott";
            string userPw = "000000";

            string connectStr = string.Format("Data Source=(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST={0})(PORT=1521))(CONNECT_DATA=(SERVICE_NAME={1})));User Id={2};Password={3};", serverAddress, serviceName, userId, userPw);

            using (OracleConnection cnn = new OracleConnection(connectStr))
            {
                cnn.Open();

                /*************************** Create *************************************/
                OracleCommand insertCmd = new OracleCommand();
                insertCmd.Connection = cnn;
                insertCmd.CommandText = "INSERT INTO USERS(ID, USERNAME) VALUES (:id, :NAME)";

                insertCmd.Parameters.Add("ID", OracleDbType.Int32);
                insertCmd.Parameters.Add("USERNAME", OracleDbType.Varchar2, 50);

                insertCmd.Parameters[0].Value = 1;
                insertCmd.Parameters[1].Value = "test";

                int affected = insertCmd.ExecuteNonQuery();
                Console.WriteLine("# Result : " + affected);


                /*************************** Read *************************************/
                // Select - ExecuteScalar
                OracleCommand selectCmd = new OracleCommand();
                selectCmd.Connection = cnn;
                selectCmd.CommandText = "SELECT USERNAME FROM USERS WHERE ID=1";

                object result = selectCmd.ExecuteScalar();
                Console.WriteLine("# Result : " + result);

                // Select - DataTable
                DataSet ds = new DataSet();
                OracleDataAdapter da = new OracleDataAdapter("SELECT * FROM USERS", cnn);
                da.Fill(ds, "USERS");

                DataTable dt = ds.Tables["USERS"];
                foreach (DataRow dr in dt.Rows)
                {
                    Console.WriteLine(string.Format("ID = {0}, USERNAME = {1}", dr["ID"], dr["USERNAME"]));
                }

                /*************************** Update *************************************/
                OracleCommand updateCmd = new OracleCommand();
                updateCmd.Connection = cnn;
                updateCmd.CommandText = "UPDATE USERS SET USERNAME = :USERNAME WHERE ID = :ID";

                updateCmd.Parameters.Add("USERNAME", OracleDbType.Varchar2, 150);
                updateCmd.Parameters.Add("ID", OracleDbType.Int32);

                updateCmd.Parameters[0].Value = "test2";
                updateCmd.Parameters[1].Value = 1;

                affected = updateCmd.ExecuteNonQuery();
                Console.WriteLine("# Result : " + affected);

                /*************************** Delete *************************************/
                OracleCommand deleteCmd = new OracleCommand();
                deleteCmd.Connection = cnn;
                deleteCmd.CommandText = "DELETE FROM USERS WHERE ID = :ID";

                deleteCmd.Parameters.Add("ID", OracleDbType.Int32);
                deleteCmd.Parameters[0].Value = 1;

                affected = deleteCmd.ExecuteNonQuery();
                Console.WriteLine("# Result : " + affected);
            }
        }
    }

}
