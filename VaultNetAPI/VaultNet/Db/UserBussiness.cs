using System.ComponentModel.DataAnnotations.Schema;

namespace VaultNet.Db
{
    public class UserBussiness
    {
        public int UserId { get; set; }

        [ForeignKey (nameof (UserId))]
        public User User { get; set; }


        public int BussinessId {  get; set; }

        [ForeignKey(nameof(BussinessId))]
        public Bussiness Bussiness { get; set; }

        public bool Active {  get; set; }
    }
}
