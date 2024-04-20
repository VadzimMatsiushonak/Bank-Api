package by.vadzimmatsiushonak.bank.api.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
//
//    @InjectMocks
//    private TransactionServiceImpl service;
//
//    @Mock
//    private TransactionRepository repository;
//
//    @Nested
//    public class TransactionServiceTestCreate {
//        @Test
//        public void save() {
//            Transaction expected = new Transaction();
//            expected.setId(ID_LONG);
//
//            Transaction Transaction = new Transaction();
//            when(repository.save(Transaction)).thenReturn(expected);
//
//
//            Transaction actual = service.save(Transaction);
//            assertEquals(expected, actual);
//            verify(repository).save(expected);
//        }
//
//        @Test
//        public void saveWithId() {
//            Transaction expected = new Transaction();
//            expected.setId(ID_LONG);
//
//            Transaction transaction = new Transaction();
//            when(repository.save(transaction)).thenReturn(expected);
//
//            Transaction transactionWithId = new Transaction();
//            transactionWithId.setId(ID_LONG);
//
//            Transaction actual = service.save(transactionWithId);
//            assertEquals(expected, actual);
//            verify(repository).save(expected);
//        }
//    }
//
//    @Nested
//    public class TransactionServiceTestFindById {
//        @Test
//        public void findById() {
//            Transaction transaction = new Transaction();
//            transaction.setId(ID_LONG);
//            when(repository.findById(ID_LONG)).thenReturn(Optional.of(transaction));
//
//
//            Transaction actual = service.findById(ID_LONG).orElse(null);
//            assertEquals(transaction, actual);
//            verify(repository).findById(ID_LONG);
//        }
//    }
//
//    @Nested
//    public class TransactionServiceTestFindAll {
//        @Test
//        public void findAll() {
//            Transaction transaction = new Transaction();
//            transaction.setId(ID_LONG);
//            List<Transaction> expected = List.of(transaction);
//            when(repository.findAll()).thenReturn(expected);
//
//
//            List<Transaction> actual = service.findAll();
//            assertEquals(expected, actual);
//            verify(repository).findAll();
//        }
//    }
//
//    @Nested
//    public class TransactionServiceTestUpdate {
//        @Test
//        public void update() {
//            Transaction expected = new Transaction();
//            expected.setId(ID_LONG);
//            when(repository.save(expected)).thenReturn(expected);
//
//
//            service.update(expected);
//            verify(repository).save(expected);
//        }
//
//        @Test
//        public void updateWithEmptyIban() {
//            Transaction expected = new Transaction();
//
//            assertThrows(
//                    NullPointerException.class,
//                    () -> service.update(expected));
//            verify(repository, times(0)).save(any());
//        }
//    }
//
//    @Nested
//    public class TransactionServiceTestDelete {
//        @Test
//        public void delete() {
//            Transaction expected = new Transaction();
//
//            service.delete(expected);
//            verify(repository).delete(expected);
//        }
//    }
//
//    @Nested
//    public class TransactionServiceTestDeleteById {
//        @Test
//        public void deleteById() {
//            service.deleteById(ID_LONG);
//            verify(repository).deleteById(ID_LONG);
//        }
//    }
}
