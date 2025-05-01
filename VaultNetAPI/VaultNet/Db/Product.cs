using ChargedChat.Db;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace VaultNet.Db
{
    public class Product
    {
        [Key, DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int ProductId { get; set; }

        public int BussinessId { get; set; }

        [ForeignKey(nameof(BussinessId))]
        public Bussiness Bussiness { get; set; }

        [Required, StringLength(100)]
        public string Name { get; set; }

        public string Description { get; set; }

        public string Image {  get; set; }

        [Required]
        public int Quantity { get; set; }

        public int UnitId {  get; set; }

        [ForeignKey(nameof(UnitId))]
        public Unit Unit { get; set; }

        public int CategoryId { get; set; }

        [ForeignKey(nameof(CategoryId))]
        public Category Category { get; set; }


        public virtual ICollection<Change> Changes { get; set; }
        public virtual ICollection<Snapshot> Snapshots { get; set; }
    }
}
