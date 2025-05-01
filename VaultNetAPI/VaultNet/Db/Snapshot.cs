using ChargedChat.Db;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace VaultNet.Db
{
    public class Snapshot
    {
        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int SnapshotId { get; set; }

        [Required]
        public int BussinessId { get; set; }

        [ForeignKey(nameof(BussinessId))]
        public Bussiness Bussiness { get; set; }

        public DateTime Date { get; set; }

        public ICollection<Product> Products { get; set; }
    }
}
